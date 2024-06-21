import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Getallpatient.css';
import { Modal, Button, Form } from 'react-bootstrap';

const AddPatientModal = ({ show, handleClose, addPatient }) => {
    const [newPatient, setNewPatient] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        address: '',
        dateOfBirth: ''
    });
    const [errors, setErrors] = useState({});

    const handleChange = (e) => {
        setNewPatient({ ...newPatient, [e.target.name]: e.target.value });
    };

    const validate = () => {
        let validationErrors = {};

        if (!newPatient.firstName) {
            validationErrors.firstName = 'First name is required';
        }
        if (!newPatient.lastName) {
            validationErrors.lastName = 'Last name is required';
        }
        if (!newPatient.email) {
            validationErrors.email = 'Email is required';
        } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(newPatient.email)) {
            validationErrors.email = 'Invalid email address';
        }
        if (!newPatient.phone) {
            validationErrors.phone = 'Phone number is required';
        } else if (!/^\d{10}$/.test(newPatient.phone)) {
            validationErrors.phone = 'Phone number must be exactly 10 digits';
        }
        if (!newPatient.address) {
            validationErrors.address = 'Address is required';
        }
        if (!newPatient.dateOfBirth) {
            validationErrors.dateOfBirth = 'Date of birth is required';
        }

        setErrors(validationErrors);

        return Object.keys(validationErrors).length === 0;
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (!validate()) {
            return;
        }

        const token = localStorage.getItem('healthjwtToken');

        fetch('http://localhost:8080/patients/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(newPatient)
        })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                alert('Patient information submitted successfully!');
                addPatient(newPatient);
                handleClose();
            })
            .catch(error => {
                console.error('There was an error submitting the form!', error);
            });
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Add Patient</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="formFirstName">
                        <Form.Label>First Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="firstName"
                            value={newPatient.firstName}
                            onChange={handleChange}
                            isInvalid={!!errors.firstName}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            {errors.firstName}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formLastName">
                        <Form.Label>Last Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="lastName"
                            value={newPatient.lastName}
                            onChange={handleChange}
                            isInvalid={!!errors.lastName}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            {errors.lastName}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formEmail">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            name="email"
                            value={newPatient.email}
                            onChange={handleChange}
                            isInvalid={!!errors.email}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            {errors.email}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formPhone">
                        <Form.Label>Phone</Form.Label>
                        <Form.Control
                            type="text"
                            name="phone"
                            value={newPatient.phone}
                            onChange={handleChange}
                            isInvalid={!!errors.phone}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            {errors.phone}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formAddress">
                        <Form.Label>Address</Form.Label>
                        <Form.Control
                            type="text"
                            name="address"
                            value={newPatient.address}
                            onChange={handleChange}
                            isInvalid={!!errors.address}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            {errors.address}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Form.Group controlId="formDateOfBirth">
                        <Form.Label>Date of Birth</Form.Label>
                        <Form.Control
                            type="date"
                            name="dateOfBirth"
                            value={newPatient.dateOfBirth}
                            onChange={handleChange}
                            isInvalid={!!errors.dateOfBirth}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            {errors.dateOfBirth}
                        </Form.Control.Feedback>
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Add Patient
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

const GetAllPatients = () => {
    const [patients, setPatients] = useState([]);
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        const fetchPatients = async () => {
            try {

                const token = localStorage.getItem('healthjwtToken');
                const response = await fetch('http://localhost:8080/patients',
                       {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`
                        }},
                );
                if (!response.ok) {
                    throw new Error('Failed to fetch data');
                }
                const data = await response.json();
                setPatients(data);
            } catch (error) {
                console.error('Error fetching patients:', error);
            }
        };

        fetchPatients();
    }, []);

    const handleShowModal = () => setShowModal(true);
    const handleCloseModal = () => setShowModal(false);

    const addPatient = (newPatient) => {
        setPatients([...patients, { patientId: patients.length + 1, ...newPatient }]);
    };

    return (
        <div className="container mt-5">
            <div className="d-flex justify-content-center align-items-center" style={{ marginBottom: '30px' }}>
                  <h1 className="mr-3 mb-0">Patient List</h1>
                  <div style={{ marginLeft: '20px' }}></div> {/* Custom margin using inline style */}
                  <button className="btn btn-primary" onClick={handleShowModal}>Add Patient</button>
            </div>
            <div className="row">
                {patients.map(patient => (
                    <div key={patient.patientId} className="col-md-6">
                        <div className="card mb-4">
                            <div className="card-body">
                                <h5 className="card-title">{patient.firstName} {patient.lastName}</h5>
                                <p className="card-text"><strong>Email:</strong> {patient.email}</p>
                                <p className="card-text"><strong>Phone:</strong> {patient.phone}</p>
                                <p className="card-text"><strong>Address:</strong> {patient.address}</p>
                                <p className="card-text"><strong>Date of Birth:</strong> {patient.dateOfBirth}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            <AddPatientModal show={showModal} handleClose={handleCloseModal} addPatient={addPatient} />
        </div>
    );
};

export default GetAllPatients;
