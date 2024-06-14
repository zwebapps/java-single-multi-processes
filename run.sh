#!/bin/bash

# Function to check and kill the process using the specified port
kill_process_on_port() {
    port=$1
    pid=$(lsof -t -i:$port)
    if [ ! -z "$pid" ]; then
        echo "Killing process $pid using port $port"
        kill -9 $pid
    fi
}

# Define the port to be used
port=5001

# Kill any process using the port
kill_process_on_port $port

# Compile the project
mvn clean compile

# Check the argument
if [ "$1" == "separate-processes" ]; then
    # Run in separate processes
    mvn exec:java -Dexec.mainClass="com.example.player.Main" -Dexec.args="separate-processes"
else
    # Run in the same process by default
    mvn exec:java -Dexec.mainClass="com.example.player.Main" -Dexec.args="same-process"
fi
