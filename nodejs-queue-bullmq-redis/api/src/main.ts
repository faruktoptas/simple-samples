import express = require("express");
import { Express, NextFunction, Request, Response } from 'express';
import { Queue, Worker, Job } from 'bullmq'

export const app = express();


const myQueue = new Queue('que', {
    connection: {
        host: "localhost",
        port: 6379
    }
});


app.get('/', async (req: Request, res: Response, next: NextFunction) => {

    await myQueue.add('myjob', { foo: 'bar'});

    res.send('Queued')
})



// const worker = new Worker('que', async (job) => {
//     console.log('processing ', job.data)
//     return true
//   }, {
//     connection: {
//       host: "localhost",
//       port: 6379
//     }
//   });
  
//   worker.on('completed', (job: Job, returnvalue: any) => {
//     console.log('completed', job.data, returnvalue);
    
//   });
  
//   worker.on('progress', (job: Job, progress: number | object) => {
//     console.log('progress', job.data, progress);
    
//   });
  
//   worker.on('failed', (job: Job, error: Error) => {
//     console.log('failed', job.data, error);
    
//   });
  
//   worker.on('error', err => {
//     // log the error
//     console.error(err);
//   });
  

const port = 3003

export const server = app.listen(port, () => {
    console.log(`server started at http://localhost:${port}`);
});

server.on('close', async () => {
    console.log('closing');
    //await myWorker.close()
});