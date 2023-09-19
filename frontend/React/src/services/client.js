import axios from "axios";
import React from 'react'

export const getCustomers = async () => {
    try {
         return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customer`)
    } catch (error) {
        throw error;
    }
}
