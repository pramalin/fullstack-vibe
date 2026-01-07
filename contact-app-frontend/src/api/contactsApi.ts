import axios from 'axios'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

export interface Contact {
  id?: number
  firstName: string
  lastName: string
  email: string
  phone?: string
  company?: string
  jobTitle?: string
  address?: string
  city?: string
  state?: string
  zipCode?: string
  country?: string
  notes?: string
  photoPath?: string
  photoFileName?: string
  createdAt?: string
  updatedAt?: string
}

export interface ContactsResponse {
  content: Contact[]
  totalElements: number
  totalPages: number
  number: number
  size: number
}

// Get all contacts with pagination
export const getAllContacts = (page: number = 0, size: number = 10) => {
  return api.get<ContactsResponse>('/contacts', {
    params: { page, size },
  })
}

// Search contacts
export const searchContacts = (searchTerm: string, page: number = 0, size: number = 10) => {
  return api.get<ContactsResponse>('/contacts/search', {
    params: { searchTerm, page, size },
  })
}

// Get contact by ID
export const getContact = (id: number) => {
  return api.get<Contact>(`/contacts/${id}`)
}

// Create contact
export const createContact = (contact: Contact, photoFile?: File) => {
  const formData = new FormData()
  formData.append('contact', new Blob([JSON.stringify(contact)], { type: 'application/json' }))
  if (photoFile) {
    formData.append('photo', photoFile)
  }

  return api.post<Contact>('/contacts', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}

// Update contact
export const updateContact = (id: number, contact: Contact, photoFile?: File) => {
  const formData = new FormData()
  formData.append('contact', new Blob([JSON.stringify(contact)], { type: 'application/json' }))
  if (photoFile) {
    formData.append('photo', photoFile)
  }

  return api.put<Contact>(`/contacts/${id}`, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
}

// Delete contact
export const deleteContact = (id: number) => {
  return api.delete(`/contacts/${id}`)
}

export default api
