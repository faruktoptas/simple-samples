import { Queue, Worker, Job } from 'bullmq'

const worker = new Worker('que', async (job) => {
  console.log('processing ', job.data)
  await new Promise((resolve, reject) => {setTimeout(resolve, 3000)})
  return true
}, {
  connection: {
    host: "localhost",
    port: 6379
  }
});

worker.on('completed', (job: Job, returnvalue: any) => {
  console.log('completed', job.data, returnvalue);
  
});

worker.on('progress', (job: Job, progress: number | object) => {
  console.log('progress', job.data, progress);
  
});

worker.on('failed', (job: Job, error: Error) => {
  console.log('failed', job.data, error);
  
});

worker.on('error', err => {
  // log the error
  console.error(err);
});

//worker.run()
