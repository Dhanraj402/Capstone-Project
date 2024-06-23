import React, { useEffect, useState } from "react";
import ClipLoader from "react-spinners/ClipLoader";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Modal from "./Modal";
import "./PatientRecords.css";

const PatientRecords = () => {
  const [patients, setPatients] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [newPatient, setNewPatient] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    address: "",
    dateOfBirth: "",
  });
  const [loading, setLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [lazyLoadCount, setLazyLoadCount] = useState(9);
  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    address: "",
    dateOfBirth: "",
  });

  useEffect(() => {
    fetchPatients();
  }, []);

  const fetchPatients = async () => {
    setLoading(true);
    try {
      const response = await fetch("http://localhost:7010/patients", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });

      if (!response.ok) {
        throw new Error(`Error fetching patients: ${response.statusText}`);
      }

      const data = await response.json();
      setPatients(data);
      toast.success("Patient record fetched successfully!");
    } catch (error) {
      console.error("Error fetching patients:", error);
      toast.error(`Error fetching patients: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewPatient((prevPatient) => ({
      ...prevPatient,
      [name]: value,
    }));
  };

  const validateForm = () => {
    let isValid = true;
    const errorsCopy = {
      firstName: "",
      lastName: "",
      email: "",
      phone: "",
      address: "",
      dateOfBirth: "",
    };

    if (!newPatient.firstName.trim()) {
      errorsCopy.firstName = "First Name is required";
      isValid = false;
    }

    if (!newPatient.lastName.trim()) {
      errorsCopy.lastName = "Last Name is required";
      isValid = false;
    }

    if (!newPatient.email.trim()) {
      errorsCopy.email = "Email is required";
      isValid = false;
    } else if (!/\S+@\S+\.\S+/.test(newPatient.email)) {
      errorsCopy.email = "Invalid email format";
      isValid = false;
    }

    if (!newPatient.phone.trim()) {
      errorsCopy.phone = "Phone number is required";
      isValid = false;
    } else if (!/^\d{10}$/.test(newPatient.phone)) {
      errorsCopy.phone = "Phone number must be 10 digits";
      isValid = false;
    }

    if (!newPatient.address.trim()) {
      errorsCopy.address = "Address is required";
      isValid = false;
    }

    if (!newPatient.dateOfBirth.trim()) {
      errorsCopy.dateOfBirth = "Date of Birth is required";
      isValid = false;
    }

    setErrors(errorsCopy);
    return isValid;
  };

  const handleAddPatient = async (e) => {
    e.preventDefault();
    if (!validateForm()) {
      return;
    }

    setLoading(true);
    try {
      const response = await fetch("http://localhost:7010/patients/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        body: JSON.stringify(newPatient),
      });

      setLoading(false);

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Error adding patient: ${errorText}`);
      }

      const contentType = response.headers.get("content-type");
      let data;
      if (contentType && contentType.indexOf("application/json") !== -1) {
        data = await response.json();
      } else {
        const textData = await response.text();
        toast.success(textData); // Show the plain text response if it's not JSON
        return;
      }

      setPatients((prevPatients) => [...prevPatients, data]);
      setIsModalOpen(false);
      setNewPatient({
        firstName: "",
        lastName: "",
        email: "",
        phone: "",
        address: "",
        dateOfBirth: "",
      });
      toast.success("Patient added successfully!");
    } catch (error) {
      setLoading(false);
      console.error("Error adding patient:", error);
      toast.error(`${error.message}`);
    }
  };

  const loadMorePatients = () => {
    setLazyLoadCount((prevCount) => prevCount + 9);
  };

  const filteredPatients = patients
    .filter((patient) =>
      `${patient.firstName} ${patient.lastName}`
        .toLowerCase()
        .includes(searchTerm.toLowerCase())
    )
    .slice(0, lazyLoadCount);

  return (
    <div className="patient-records">
      <h2>Patient Records</h2>
      <input
        type="text"
        placeholder="Search patients..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="search-bar"
      />
      <button className="btn" onClick={() => setIsModalOpen(true)}>
        Add Patient
      </button>

      {loading ? (
        <div className="spinner">
          <ClipLoader size={50} color={"#123abc"} loading={loading} />
        </div>
      ) : (
        <>
          <table className="patient-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Address</th>
                <th>Date of Birth</th>
              </tr>
            </thead>
            <tbody>
              {filteredPatients.map((patient) => (
                <tr key={patient.patientId}>
                  <td>{patient.patientId}</td>
                  <td>{patient.firstName}</td>
                  <td>{patient.lastName}</td>
                  <td>{patient.email}</td>
                  <td>{patient.phone}</td>
                  <td>{patient.address}</td>
                  <td>{new Date(patient.dateOfBirth).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>

          {filteredPatients.length < patients.length && (
            <button className="load-more-button" onClick={loadMorePatients}>
              Load More
            </button>
          )}
        </>
      )}

      <Modal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        className="patient-modal"
      >
        <h2 className="modal__header">Add New Patient</h2>
        <form className="modal__form" onSubmit={handleAddPatient}>
          <label>First Name</label>
          <input
            type="text"
            name="firstName"
            value={newPatient.firstName}
            onChange={handleInputChange}
            required
          />
          {errors.firstName && (
            <span className="error-message">{errors.firstName}</span>
          )}
          <label>Last Name</label>
          <input
            type="text"
            name="lastName"
            value={newPatient.lastName}
            onChange={handleInputChange}
            required
          />
          {errors.lastName && (
            <span className="error-message">{errors.lastName}</span>
          )}
          <label>Email</label>
          <input
            type="email"
            name="email"
            value={newPatient.email}
            onChange={handleInputChange}
            required
          />
          {errors.email && (
            <span className="error-message">{errors.email}</span>
          )}
          <label>Phone</label>
          <input
            type="text"
            name="phone"
            value={newPatient.phone}
            onChange={handleInputChange}
            required
          />
          {errors.phone && (
            <span className="error-message">{errors.phone}</span>
          )}
          <label>Address</label>
          <input
            type="text"
            name="address"
            value={newPatient.address}
            onChange={handleInputChange}
            required
          />
          {errors.address && (
            <span className="error-message">{errors.address}</span>
          )}
          <label>Date of Birth</label>
          <input
            type="date"
            name="dateOfBirth"
            value={newPatient.dateOfBirth}
            onChange={handleInputChange}
            required
          />
          {errors.dateOfBirth && (
            <span className="error-message">{errors.dateOfBirth}</span>
          )}
          <button className="btn" type="submit" disabled={loading}>
            {loading ? <ClipLoader size={20} color={"#fff"} /> : "Add Patient"}
          </button>
        </form>
      </Modal>

      <ToastContainer />
    </div>
  );
};

export default PatientRecords;
