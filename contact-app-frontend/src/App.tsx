import React, { useState, useEffect } from 'react'
import { getAllContacts, searchContacts, createContact, updateContact, deleteContact, Contact } from './api/contactsApi'
import { ContactForm, ContactList, ContactDetail } from './components/ContactComponents'
import { Button } from './components/ui/Button'
import { Input } from './components/ui/Input'
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from './components/ui/Dialog'
import { Plus, Search } from 'lucide-react'

const App: React.FC = () => {
  const [contacts, setContacts] = useState<Contact[]>([])
  const [totalPages, setTotalPages] = useState(0)
  const [currentPage, setCurrentPage] = useState(0)
  const [isLoading, setIsLoading] = useState(false)
  const [searchQuery, setSearchQuery] = useState('')
  const [isDialogOpen, setIsDialogOpen] = useState(false)
  const [editingContact, setEditingContact] = useState<Contact | undefined>()
  const [viewingContact, setViewingContact] = useState<Contact | undefined>()

  useEffect(() => {
    loadContacts()
  }, [currentPage, searchQuery])

  const loadContacts = async () => {
    try {
      setIsLoading(true)
      const response = searchQuery
        ? await searchContacts(searchQuery, currentPage, 10)
        : await getAllContacts(currentPage, 10)
      setContacts(response.data.content)
      setTotalPages(response.data.totalPages)
    } catch (error) {
      console.error('Failed to load contacts:', error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleCreateContact = async (contact: Contact) => {
    try {
      setIsLoading(true)
      await createContact(contact)
      setIsDialogOpen(false)
      setCurrentPage(0)
      loadContacts()
    } catch (error) {
      console.error('Failed to create contact:', error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleUpdateContact = async (contact: Contact) => {
    try {
      setIsLoading(true)
      if (editingContact?.id) {
        await updateContact(editingContact.id, contact)
        setEditingContact(undefined)
        setIsDialogOpen(false)
        loadContacts()
      }
    } catch (error) {
      console.error('Failed to update contact:', error)
    } finally {
      setIsLoading(false)
    }
  }

  const handleDeleteContact = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this contact?')) {
      try {
        setIsLoading(true)
        await deleteContact(id)
        loadContacts()
      } catch (error) {
        console.error('Failed to delete contact:', error)
      } finally {
        setIsLoading(false)
      }
    }
  }

  const handleSearch = (query: string) => {
    setSearchQuery(query)
    setCurrentPage(0)
  }

  return (
    <div className="min-h-screen bg-gray-100">
      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        {/* Header */}
        <div className="bg-white rounded-lg shadow mb-6 p-6">
          <h1 className="text-3xl font-bold text-gray-900 mb-4">Contact Manager</h1>
          
          <div className="flex gap-4 mb-4">
            <div className="flex-1 relative">
              <Search className="absolute left-3 top-3 text-gray-400 w-5 h-5" />
              <Input
                placeholder="Search contacts by name or email..."
                value={searchQuery}
                onChange={(e) => handleSearch(e.target.value)}
                className="pl-10"
              />
            </div>
            <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
              <DialogTrigger asChild>
                <Button
                  onClick={() => {
                    setEditingContact(undefined)
                    setIsDialogOpen(true)
                  }}
                >
                  <Plus className="w-4 h-4 mr-2" />
                  New Contact
                </Button>
              </DialogTrigger>
              <DialogContent>
                <DialogHeader>
                  <DialogTitle>
                    {editingContact ? 'Edit Contact' : 'Create New Contact'}
                  </DialogTitle>
                </DialogHeader>
                <ContactForm
                  contact={editingContact}
                  onSubmit={editingContact ? handleUpdateContact : handleCreateContact}
                  onCancel={() => {
                    setIsDialogOpen(false)
                    setEditingContact(undefined)
                  }}
                  isLoading={isLoading}
                />
              </DialogContent>
            </Dialog>
          </div>
        </div>

        {/* Contacts Table */}
        <div className="bg-white rounded-lg shadow overflow-hidden">
          <ContactList
            contacts={contacts}
            onEdit={(contact) => {
              setEditingContact(contact)
              setIsDialogOpen(true)
            }}
            onDelete={handleDeleteContact}
            onView={setViewingContact}
            isLoading={isLoading}
          />
        </div>

        {/* Pagination */}
        {totalPages > 1 && (
          <div className="mt-4 flex justify-center gap-2">
            <Button
              variant="outline"
              onClick={() => setCurrentPage(Math.max(0, currentPage - 1))}
              disabled={currentPage === 0}
            >
              Previous
            </Button>
            <span className="px-4 py-2 text-sm font-medium">
              Page {currentPage + 1} of {totalPages}
            </span>
            <Button
              variant="outline"
              onClick={() => setCurrentPage(Math.min(totalPages - 1, currentPage + 1))}
              disabled={currentPage >= totalPages - 1}
            >
              Next
            </Button>
          </div>
        )}
      </div>

      {/* Detail Modal */}
      {viewingContact && (
        <ContactDetail contact={viewingContact} onClose={() => setViewingContact(undefined)} />
      )}
    </div>
  )
}

export default App
