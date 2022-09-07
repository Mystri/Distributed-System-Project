# cs425-MP1-Yuankai-Boyou

## MP1
Run `sh startServer.sh` on all the machines and then use a different terminal and run `sh startClient.sh` as the client to type the query.
Once the client side is started, you will see `$ ` in the console and you can type the grep query.
The query supports:
`grep keyword` to return the lines that contain the word in the files.
`grep -c keyword` to return the number of lines that contains the word in the files.
`grep -E regex` to return the lines that contain the regular expression pattern in the files.
`grep -Ec regex` to return the number of lines that contains the regular expression pattern in the files.
Other queries are invalid.