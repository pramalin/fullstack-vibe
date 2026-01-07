-- Initial database setup for Contact App
-- This script initializes the database with sample data

-- Create the contacts table
CREATE TABLE IF NOT EXISTS contacts (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20),
    company VARCHAR(255),
    job_title VARCHAR(255),
    address TEXT,
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    country VARCHAR(100),
    notes TEXT,
    photo_path VARCHAR(500),
    photo_filename VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample contacts
INSERT INTO contacts (first_name, last_name, email, phone, company, job_title, address, city, state, zip_code, country, notes, created_at, updated_at)
VALUES 
    ('John', 'Doe', 'john.doe@example.com', '+12025551234', 'Tech Corp', 'Senior Software Engineer', '123 Main St', 'San Francisco', 'CA', '94105', 'United States', 'Lead developer on mobile team', NOW(), NOW()),
    ('Jane', 'Smith', 'jane.smith@example.com', '+14155555678', 'Design Studios', 'UX Designer', '456 Oak Ave', 'New York', 'NY', '10001', 'United States', 'Expert in user interface design', NOW(), NOW()),
    ('Michael', 'Johnson', 'michael.johnson@example.com', '+13105559999', 'Finance Group', 'Financial Analyst', '789 Pine Rd', 'Los Angeles', 'CA', '90001', 'United States', 'Specializes in market analysis', NOW(), NOW()),
    ('Sarah', 'Williams', 'sarah.williams@example.com', '+14025556789', 'Marketing Pro', 'Marketing Manager', '321 Elm St', 'Chicago', 'IL', '60601', 'United States', 'Digital marketing expert', NOW(), NOW()),
    ('Robert', 'Brown', 'robert.brown@example.com', '+15125554321', 'BuildTech Ltd', 'Project Manager', '654 Maple Dr', 'Austin', 'TX', '78701', 'United States', 'Agile certification holder', NOW(), NOW()),
    ('Emily', 'Davis', 'emily.davis@example.com', '+12165558765', 'Cloud Systems', 'DevOps Engineer', '987 Cedar Ln', 'Seattle', 'WA', '98101', 'United States', 'AWS and Kubernetes expert', NOW(), NOW()),
    ('David', 'Martinez', 'david.martinez@example.com', '+13125559876', 'Data Insights', 'Data Scientist', '147 Birch Way', 'Denver', 'CO', '80202', 'United States', 'Machine learning specialist', NOW(), NOW()),
    ('Lisa', 'Anderson', 'lisa.anderson@example.com', '+14045552468', 'Startup Hub', 'Founder & CEO', '258 Spruce St', 'Atlanta', 'GA', '30303', 'United States', 'Serial entrepreneur', NOW(), NOW()),
    ('James', 'Taylor', 'james.taylor@example.com', '+15035557890', 'Legal Associates', 'Senior Attorney', '369 Walnut Ave', 'Portland', 'OR', '97201', 'United States', 'Corporate law specialist', NOW(), NOW()),
    ('Jessica', 'Wilson', 'jessica.wilson@example.com', '+16195553210', 'Healthcare Plus', 'Operations Director', '741 Ash Rd', 'San Diego', 'CA', '92101', 'United States', 'Healthcare management expert', NOW(), NOW());

-- Create index for faster email lookups
CREATE INDEX IF NOT EXISTS idx_email ON contacts(email);

-- Create index for faster name searches
CREATE INDEX IF NOT EXISTS idx_name ON contacts(first_name, last_name);
