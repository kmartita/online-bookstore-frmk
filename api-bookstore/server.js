const express = require('express');
const bodyParser = require('body-parser');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(bodyParser.json());

// Data for books (in-memory storage)
let books = [
    { id: 1, title: 'Book 1', pageCount: 200, completed: false, description: null, authorId: 1 },
    { id: 2, title: 'Book 2', pageCount: 130, completed: true, description: 'A great book!', authorId: null }
];

// Data for authors (in-memory storage)
let authors = [
    { id: 1, firstName: 'Firstname', lastName: 'Lastname', age: null }
];

function isBoolean(value) {
    return typeof value === 'boolean';
}

function isInteger(value) {
    return Number.isInteger(value);
}

// Books API
app.get('/api/v1/Books', (req, res) => {
    res.json({ "Books": books }); // Retrieve a list of all books
});

app.get('/api/v1/Books/:id', (req, res) => {
    const book = books.find(b => b.id === parseInt(req.params.id));
    if (!book) return res.status(404).send('Book not found'); // Handle book not found
    res.json(book);
});

app.post('/api/v1/Books', (req, res) => {
    const { title, pageCount, completed, authorId, description } = req.body;

    // Validate required fields
    if (!title || !pageCount || completed === undefined) {
        return res.status(400).json({ message: 'Title, pageCount, and completed are required fields' });
    }

    if (!isInteger(pageCount)) {
       return res.status(400).json({ message: 'pageCount must be an integer' });
    }
    if (!isBoolean(completed)) {
       return res.status(400).json({ message: 'completed must be a boolean' });
    }
    if (authorId !== undefined && authorId !== null && !isInteger(authorId)) {
        return res.status(400).json({ message: 'authorId must be an integer or null' });
    }

    // Validate authorId exists (if provided)
    if (authorId) {
        const authorExists = authors.some(author => author.id === parseInt(authorId));
        if (!authorExists) {
            return res.status(400).json({ message: 'Invalid authorId: Author not found' });
        }

        // Check if a book with the same title already exists for the author
        const bookExists = books.some(
            book => book.authorId === parseInt(authorId) && book.title === title
        );

        if (bookExists) {
            return res.status(400).json({ message: 'A book with the same title already exists for this author' });
        }
    }

    const newBook = {
        id: books.length + 1,
        title,
        pageCount: parseInt(pageCount),
        completed,
        description: description || null, // description is not required
        authorId: authorId || null // Allow null authorId
    };

    books.push(newBook);
    res.status(201).json(newBook); // Return 201 Created
});

app.put('/api/v1/Books/:id', (req, res) => {
    const id = parseInt(req.params.id);
    const book = books.find(b => b.id === id);
    if (!book) return res.status(404).send('Book not found');

    // Update any fields provided in the request body
    Object.keys(req.body).forEach(key => {
        book[key] = req.body[key];
    });

    res.json(book);
});

app.delete('/api/v1/Books/:id', (req, res) => {
    books = books.filter(b => b.id !== parseInt(req.params.id));
    res.status(204).send(); // Delete book and return 204 No Content
});

// Authors API
app.get('/api/v1/Authors', (req, res) => {
    res.json({ "Authors": authors }); // Retrieve a list of all authors
});

app.get('/api/v1/Authors/:id', (req, res) => {
    const author = authors.find(a => a.id === parseInt(req.params.id));
    if (!author) return res.status(404).send('Author not found'); // Handle author not found
    res.json(author);
});

app.post('/api/v1/Authors', (req, res) => {
    const { firstName, lastName, age } = req.body;

    // Validate required fields
    if (!firstName || !lastName) {
        return res.status(400).json({ message: 'firstName and lastName are required fields' });
    }

    const newAuthor = {
        id: authors.length + 1,
        firstName,
        lastName,
        age: age !== undefined ? age : null // age is not required
    };

    authors.push(newAuthor);
    res.status(201).json(newAuthor); // Return 201 Created
});

app.put('/api/v1/Authors/:id', (req, res) => {
    const id = parseInt(req.params.id);
    const author = authors.find(a => a.id === id);
    if (!author) return res.status(404).send('Author not found');

    // Update any fields provided in the request body
    Object.keys(req.body).forEach(key => {
        author[key] = req.body[key];
    });

    res.json(author);
});

app.delete('/api/v1/Authors/:id', (req, res) => {
    authors = authors.filter(a => a.id !== parseInt(req.params.id));
    res.status(204).send(); // Delete author and return 204 No Content
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`); // Server starts listening on the specified port
});