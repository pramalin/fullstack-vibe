#!/bin/bash

# Contact App Docker Management Script
# This script helps manage the Contact Manager application with Docker Compose

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DOCKER_COMPOSE_FILE="${SCRIPT_DIR}/docker-compose.yml"

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Functions
print_header() {
    echo -e "${BLUE}╔════════════════════════════════════════════════════════════╗${NC}"
    echo -e "${BLUE}║${NC} $1"
    echo -e "${BLUE}╚════════════════════════════════════════════════════════════╝${NC}"
}

print_success() {
    echo -e "${GREEN}✓${NC} $1"
}

print_error() {
    echo -e "${RED}✗${NC} $1"
}

print_info() {
    echo -e "${YELLOW}ℹ${NC} $1"
}

start_services() {
    print_header "Starting Contact Manager Application"
    
    print_info "Building Docker images..."
    docker-compose -f "$DOCKER_COMPOSE_FILE" build --no-cache
    
    print_info "Starting services..."
    docker-compose -f "$DOCKER_COMPOSE_FILE" up -d
    
    print_success "Services started!"
    print_info "Waiting for services to be healthy..."
    
    # Wait for services
    sleep 10
    
    # Check health
    if docker-compose -f "$DOCKER_COMPOSE_FILE" ps | grep -q "contact-app-db.*healthy"; then
        print_success "Database is healthy!"
    else
        print_error "Database is not healthy yet. Waiting..."
        sleep 10
    fi
    
    print_success "Application is ready!"
    echo ""
    print_header "Access Points"
    echo -e "Frontend:  ${GREEN}http://localhost:5173${NC}"
    echo -e "Backend:   ${GREEN}http://localhost:8080/api${NC}"
    echo -e "Database:  ${GREEN}localhost:5432 (postgres/postgres)${NC}"
}

stop_services() {
    print_header "Stopping Contact Manager Application"
    docker-compose -f "$DOCKER_COMPOSE_FILE" down
    print_success "Services stopped!"
}

restart_services() {
    print_header "Restarting Contact Manager Application"
    docker-compose -f "$DOCKER_COMPOSE_FILE" restart
    print_success "Services restarted!"
}

view_logs() {
    SERVICE=$1
    if [ -z "$SERVICE" ]; then
        print_header "Application Logs (All Services)"
        docker-compose -f "$DOCKER_COMPOSE_FILE" logs -f
    else
        print_header "Logs for $SERVICE"
        docker-compose -f "$DOCKER_COMPOSE_FILE" logs -f "$SERVICE"
    fi
}

status() {
    print_header "Service Status"
    docker-compose -f "$DOCKER_COMPOSE_FILE" ps
}

cleanup() {
    print_header "Cleaning Up"
    print_error "This will remove all containers, networks, and volumes!"
    read -p "Are you sure? (yes/no): " CONFIRM
    
    if [ "$CONFIRM" = "yes" ]; then
        docker-compose -f "$DOCKER_COMPOSE_FILE" down -v
        print_success "Cleanup completed!"
    else
        print_info "Cleanup cancelled."
    fi
}

access_database() {
    print_header "Accessing PostgreSQL Database"
    print_info "Entering PostgreSQL CLI..."
    docker-compose -f "$DOCKER_COMPOSE_FILE" exec postgres psql -U postgres -d contact_app_db
}

access_backend_shell() {
    print_header "Accessing Backend Container"
    print_info "Entering backend shell..."
    docker-compose -f "$DOCKER_COMPOSE_FILE" exec backend /bin/sh
}

access_frontend_shell() {
    print_header "Accessing Frontend Container"
    print_info "Entering frontend shell..."
    docker-compose -f "$DOCKER_COMPOSE_FILE" exec frontend /bin/sh
}

rebuild_services() {
    print_header "Rebuilding Services"
    docker-compose -f "$DOCKER_COMPOSE_FILE" build --no-cache
    print_success "Services rebuilt!"
}

show_help() {
    cat << EOF
${BLUE}Contact Manager - Docker Management Script${NC}

Usage: $0 <command>

Commands:
  ${GREEN}start${NC}           - Start all services (build + up)
  ${GREEN}stop${NC}            - Stop all services
  ${GREEN}restart${NC}         - Restart all services
  ${GREEN}status${NC}          - Show service status
  ${GREEN}logs${NC} [service]  - View logs (service: backend, frontend, postgres)
  ${GREEN}rebuild${NC}         - Rebuild Docker images
  ${GREEN}cleanup${NC}         - Remove all containers, networks, and volumes
  ${GREEN}db${NC}              - Access PostgreSQL CLI
  ${GREEN}shell-backend${NC}   - Access backend container shell
  ${GREEN}shell-frontend${NC}  - Access frontend container shell
  ${GREEN}help${NC}            - Show this help message

Examples:
  $0 start
  $0 logs backend
  $0 db
  $0 shell-frontend

EOF
}

# Main script logic
case "${1:-help}" in
    start)
        start_services
        ;;
    stop)
        stop_services
        ;;
    restart)
        restart_services
        ;;
    status)
        status
        ;;
    logs)
        view_logs "$2"
        ;;
    cleanup)
        cleanup
        ;;
    rebuild)
        rebuild_services
        ;;
    db)
        access_database
        ;;
    shell-backend)
        access_backend_shell
        ;;
    shell-frontend)
        access_frontend_shell
        ;;
    help)
        show_help
        ;;
    *)
        print_error "Unknown command: $1"
        echo ""
        show_help
        exit 1
        ;;
esac
