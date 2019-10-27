# Event log
### Event logging to hsql db based on the input log file
 
 In this Project, following tasks are required to be done
 
 * Read the input file given by path as command line argument.
 * Read each line containing json data of each record.
 * Read eventId, timestamps and other fields.
 * Process the values and output the relevant fields (eventId, duration, type, host and alert) onto the database (hsqldb).

The jar file is available in the path ``out\artifacts\EventTask\EventTask.jar``

### Command to run the jar file
``
java -cp EventTask.jar Application <path_to_log_file>
``

Example:
``
java -cp EventTask.jar Application C:\\Users\\salman\\Documents\\logfile.txt
``

### NOTE:
Before running the jar, you need to setup the hsql db on the system where you wish to run the jar file.

Instructions are available [here](https://www.tutorialspoint.com/hsqldb/hsqldb_installation.htm)