# Graph Generation

--------------------------------------------------------------------------------------------------------------------------------------------

## FlameGraphs Generation


### Offline Graph

The offline version of the graph implies the graph generated during the student experimenting in his local machine.
The TA can see the offline version of the offline version of the graph as follows.

1. Go to web-server folder.
2. open flamegraph.html

### Experiment Based Graph generation

The following instructions could be followed to generate the flamegraph for the experiment the TA will be running.

1. run "rm isolate*" in web-server folder.
2. run "rm flamegraph.html" in web-server folder.
3. While running the experiment start the node js server using the following command "node --perf server.js"
4. After completing the experiment and stopping the nodejs server run the following command in the web-server folder.

node --prof-process --preprocess -j isolate*.log | flamebearer

--------------------------------------------------------------------------------------------------------------------------------------------

## Throughtput / Timeout Graphs Generation

### Offline Graph

The offline version of the graph implies the graph generated during the student experimenting in his local machine.
The TA can see the offline version of the offline version of the graph as follows.

I have included the mongodb dump of my local runs in the root folder.

1. restore the mongodump https://www.mongodb.com/docs/manual/tutorial/backup-and-restore-tools/#:~:text=The%20mongorestore%20utility%20restores%20a,a%20subset%20of%20the%20backup.
2. Go into mongodb stress-test database and see one of the documents in snapshot collection and copy the snapshotId for that document.
3. Navigate to visualzier folder and change line number 26
SNAPSHOT_Id=<new-snapshot-id>
and insert the new snapshot id and run visualizer.py to generate the throughput graph.


### Experiment Based Graph generation

1. In java console when we run a stress test the following line printed

> Task :StressTestMain.main()
Snapshot id : 1651648453

copy the snapshot if from the console.

2. Navigate to visualzier folder and change line number 26
SNAPSHOT_Id=<new-snapshot-id>
and insert the new snapshot id and run visualizer.py to generate the throughput graph.