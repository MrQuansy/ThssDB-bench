#!/bin/bash

# Set the path to the test.jar file
TEST_JAR_PATH="/opt/test/test.jar"

# Function to cleanup and terminate the process
function cleanup_and_terminate() {
    # Terminate the process
    kill -9 $1

    # Cleanup temporary directory and OS cache
    rm -rf "$2"
    sync
}

# Loop over directories from group1 to group40
for groupDir in {1..40}; do
    # Check if the directory exists
    if [ -d "$groupDir" ]; then
        echo "Testing $groupDir..."

        # Create a temporary directory and copy read_committed.jar
        tempDir=$(mktemp -d)
	    if [ -e "$groupDir/read_committed.jar" ]; then
	        cp "$groupDir/read_committed.jar" "$tempDir"
	    elif [ -e "$groupDir/read_committed_50.jar" ]; then
	        cp "$groupDir/read_committed_50.jar" "$tempDir"
	    elif [ -e "$groupDir/read_committed_100.jar" ]; then
	        cp "$groupDir/read_committed_100.jar" "$tempDir"
	    elif [ -e "$groupDir/read_committed_500.jar" ]; then
	        cp "$groupDir/read_committed_500.jar" "$tempDir"
	    else
	        echo "No suitable read_committed JAR found in $groupDir. Skipping..."
	        continue
	    fi


        # Start read_committed.jar
        cd "$tempDir" || exit
        java -jar read_committed*.jar &

        # Store the process ID for termination
        read_committed_process=$!

        # Wait for the process to start (modify sleep duration as needed)
        sleep 5

        # Run the test.jar program for RC level
        java -jar "$TEST_JAR_PATH" "${groupDir#group}" "RC"

        # Cleanup and terminate read_committed.jar
        cleanup_and_terminate $read_committed_process "$tempDir"

        # Switch back to the original directory
        cd - || exit

        # Create a temporary directory and copy serializable.jar
        tempDir=$(mktemp -d)
        cp "$groupDir/serializable.jar" "$tempDir"

        # Start serializable.jar
        cd "$tempDir" || exit
        java -jar serializable.jar &

        # Store the process ID for termination
        serializable_process=$!

        # Wait for the process to start (modify sleep duration as needed)
        sleep 5

        # Run the test.jar program for S level
        java -jar "$TEST_JAR_PATH" "${groupDir#group}" "S"

        # Cleanup and terminate serializable.jar
        cleanup_and_terminate $serializable_process "$tempDir"

        # Switch back to the original directory
        cd - || exit
    fi
done
