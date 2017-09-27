#!/bin/bash
if [ $2 ]
then
	java -jar server.jar -l levels/$1 -c "java searchclient.SearchClient -dfs" -g 50 -t 300
else
	java -jar server.jar -l levels/$1 -c "java searchclient.SearchClient" -g 50 -t 300
fi