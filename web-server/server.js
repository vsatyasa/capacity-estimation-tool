
const express = require('express');
const { Image } = require('image-js');

const app = express();
const morgan  = require('morgan');

app.use(morgan(':method :url :status :res[content-length] - :response-time ms'));
let image = null;

app.get('/stress-test', async (req, res) => {
   let grey = image.grey({algorithm:'yellow'});
   res.status(200).send('Successful response.');
});


app.get('/hc', async (req, res) => {
    res.status(200).send({status: "Healthy"});
});

app.listen(3000, async () =>  {
   //load the image during startup of the server
   image = await Image.load('./rn-new.jpeg');
   console.log('Example app is listening on port 3000.')
});
