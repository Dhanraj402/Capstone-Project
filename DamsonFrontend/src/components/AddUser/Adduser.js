import React, { useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

const Adduser = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    const token = localStorage.getItem('healthjwtToken');
    console.log(token);
    if (!token) {
      //setError('Authentication token is missing. Please log in again.');
      setTimeout(() => {
        setError('Authentication token is missing. Please log in again.');
      }, 3000); // 3 seconds delay
      setError('Authentication token is missing. Please log in again.');
      // Redirect or do something else upon successful login
     // Example: window.location.href = '/Login';
      return;
    }

    try {
      const response = await axios.post('YOUR_ENDPOINT', {
        username,
        password,
        role,
      }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      // Handle success response
      setSuccess('User successfully created!');
      setError('');
    } catch (error) {
      // Handle error response
      setError('Error creating user. Please try again.');
      setSuccess('');
    }
  };

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card mt-5">
            <div className="card-body">
              <h2 className="card-title text-center">Create User</h2>
              <form onSubmit={handleSubmit}>
                <div className="form-group">
                  <label htmlFor="username">Username:</label>
                  <input
                    type="text"
                    id="username"
                    className="form-control"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="password">Password:</label>
                  <input
                    type="password"
                    id="password"
                    className="form-control"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="role">Role:</label>
                  <input
                    type="text"
                    id="role"
                    className="form-control"
                    value={role}
                    onChange={(e) => setRole(e.target.value)}
                    required
                  />
                </div>
                {error && <p className="text-danger">{error}</p>}
                {success && <p className="text-success">{success}</p>}
                <button type="submit" className="btn btn-primary btn-block">
                  Submit
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Adduser;
