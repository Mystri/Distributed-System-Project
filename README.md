# cs425-Team05-Yuankai-Boyou

## MP1 Log Searcher
Run `sh startServer.sh` on all the machines to initiate the server. Then on the client you want to use, open a different
terminal and run `sh startClient.sh` as the client to type the query. Once the client side is started, you will see `$ `
in the console and you can type the grep query. <br><br>
The query will be the same as doing `grep` in the command line. We assume all the log files are stored under the `logFiles`
folder and has the extension `.log`. Therefore, you will need to use `./logFiles/*.log` as the file location.<br><br>
To return the lines that contain the keyword in the files: `grep keyword ./logFiles/*.log`.<br>
To return the number of lines that contains the word in the files: `grep -c keyword ./logFiles/*.log`.<br>
To return the lines that contain the regular expression pattern in the files: `grep -E regex ./logFiles/*.log`.<br>
To return the number of lines that contains the regular expression pattern in the files: `grep -Ec regex`.<br>