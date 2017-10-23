#!/bin/bash
if [ "$2" == "dfs" ]
then
	java -jar server.jar -l levels/$1 -c "java searchclient.SearchClient -dfs" -g 50 -t 300
elif [ "$2" == "bfs" ]
then
	java -jar server.jar -l levels/$1 -c "java searchclient.SearchClient" -g 50 -t 300
elif [ "$2" == "astar" ]
then
	java -jar server.jar -l levels/$1 -c "java searchclient.SearchClient -astar" -g 50 -t 300
elif [ "$2" == "wastar" ]
then
	java -jar server.jar -l levels/$1 -c "java searchclient.SearchClient -wastar" -g 50 -t 300
elif [ "$2" == "greedy" ]
then
	java -jar server.jar -l levels/$1 -c "java searchclient.SearchClient -greedy" -g 50 -t 300
else
	echo "not a valid search strategy"
	echo "please pass the level and search strategy as parameters"
fi
