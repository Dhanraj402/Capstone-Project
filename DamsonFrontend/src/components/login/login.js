import React, { useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();

    console.log(email,password);
    localStorage.setItem('healthjwtToken', "Mytoken");
    try {
      const response = await axios.post('http://localhost:7010/authenticate', {
        email,
        password,
      });

      const token = response.data.token;
      const expirationTime = new Date().getTime() + 10 * 24 * 60 * 60 * 1000; // 10 days from now

      localStorage.setItem('healthjwtToken', "Mytoken");
      localStorage.setItem('expirationTime', expirationTime);
      

      // Redirect or do something else upon successful login
       //Example: window.location.href = '/dashb//oard';
    } catch (error) {
      setError('Login failed. Please check your email and password.');
    }
  };

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card mt-5">
            <div className="card-body">
              <h2 className="card-title text-center">Login</h2>
              <form onSubmit={handleLogin}>
                <div className="form-group">
                  <label htmlFor="email">Email:</label>
                  <input
                    type="email"
                    id="email"
                    className="form-control"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
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
                {error && <p className="text-danger">{error}</p>}
                <button type="submit" className="btn btn-primary btn-block">
                  Login
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Login;
