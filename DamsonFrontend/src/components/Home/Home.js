import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import NavBar from '../Navbar/Navbar';
import Header from '../Header/Header';
import Footer from '../Footer/Footer';

const Home = () => {
  return (
    <div className="d-flex flex-column min-vh-100">
     <Header/>
      <NavBar/>
      <main className="flex-grow-1">
        <div className="container my-5">
          <h2>Home Page Content</h2>
        </div>
      </main>
     <Footer/>
    </div>
  );
};

export default Home;
