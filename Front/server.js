// server.js

const express = require('express');
const bodyParser = require('body-parser');
const session = require('express-session');
const bcrypt = require('bcrypt');
const mongoose = require('mongoose');
const path = require('path'); 

const app = express();
const port = 3000;

app.use(bodyParser.urlencoded({ extended: true }));
app.use(session({ secret: 'your-secret-key', resave: true, saveUninitialized: true }));

mongoose.connect('mongodb://localhost:27017/your-database', { useNewUrlParser: true, useUnifiedTopology: true });

const User = mongoose.model('User', {
    email: String,
    password: String,
});

// Serve static files (HTML, CSS, images)
app.use(express.static(path.join(__dirname, 'public')));


// Define routes
app.get('/', (req, res) => {
    // Check if the user is authenticated
    if (req.session.userId) {
        res.sendFile(__dirname + '/public/index.html');
    } else {
        res.redirect('/login');
    }
});

app.get('/login', (req, res) => {
    res.sendFile(__dirname + '/public/login.html');
});

app.post('/login', async (req, res) => {
    const { email, password } = req.body;

    // Find the user in the database by email
    const user = await User.findOne({ email });

    // Check if the user exists and the password is correct
    if (user && bcrypt.compareSync(password, user.password)) {
        // Set the user ID in the session to indicate the user is logged in
        req.session.userId = user._id;
        res.redirect('/');
    } else {
        res.send('Invalid email or password');
    }
});

app.get('/logout', (req, res) => {
    // Destroy the session to log the user out
    req.session.destroy((err) => {
        res.redirect('/login');
    });
});

app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});
