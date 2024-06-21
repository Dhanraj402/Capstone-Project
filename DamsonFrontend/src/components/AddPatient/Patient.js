import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const Patient = () => {
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        address: '',
        dateOfBirth: ''
    });

    const [errors, setErrors] = useState({});

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
        validateField(name, value);
    };

    const validateField = (name, value) => {
        let error = '';
        switch (name) {
            case 'firstName':
            case 'lastName':
                if (!value.trim()) {
                    error = 'This field is required';
                }
                break;
            case 'email':
                if (!/\S+@\S+\.\S+/.test(value)) {
                    error = 'Invalid email address';
                }
                break;
            case 'phone':
                if (!/^\d{10}$/.test(value)) {
                    error = 'Phone number must be 10 digits';
                }
                break;
            case 'address':
                if (!value.trim()) {
                    error = 'This field is required';
                }
                break;
            default:
                break;
        }
        setErrors({ ...errors, [name]: error });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        
        const token = localStorage.getItem('healthjwtToken');

        fetch('http://localhost:8080/patients/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                alert('Patient information submitted successfully!');
            })
            .catch(error => {
                console.error('There was an error submitting the form!', error);
            });
    };

    return (
        <div className="container">
            <h1 className="text-2xl font-bold mb-4">Add New Patient</h1>
            <form onSubmit={handleSubmit} className="space-y-4">
                <div className="form-group">
                    <label htmlFor="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" value={formData.firstName} onChange={handleChange} className="form-control" />
                    {errors.firstName && <span className="text-danger">{errors.firstName}</span>}
                </div>
                <div className="form-group">
                    <label htmlFor="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName" value={formData.lastName} onChange={handleChange} className="form-control" />
                    {errors.lastName && <span className="text-danger">{errors.lastName}</span>}
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} className="form-control" />
                    {errors.email && <span className="text-danger">{errors.email}</span>}
                </div>
                <div className="form-group">
                    <label htmlFor="phone">Phone</label>
                    <input type="tel" id="phone" name="phone" value={formData.phone} onChange={handleChange} className="form-control" />
                    {errors.phone && <span className="text-danger">{errors.phone}</span>}
                </div>
                <div className="form-group">
                    <label htmlFor="address">Address</label>
                    <input type="text" id="address" name="address" value={formData.address} onChange={handleChange} className="form-control" />
                    {errors.address && <span className="text-danger">{errors.address}</span>}
                </div>
                <div className="form-group">
                    <label htmlFor="dateOfBirth">Date of Birth</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" value={formData.dateOfBirth} onChange={handleChange} className="form-control" />
                </div>
                <button type="submit" className="btn btn-primary">Save</button>
            </form>
        </div>
    );
};

export default Patient;
