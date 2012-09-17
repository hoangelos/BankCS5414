Included files:

bank_1/ - main project directory
bank_1/src/ - source files
bank_1/bin/ - compiled classes; can run from here if desired
bank_1.jar - pre-built jar; please test using this
example_names - example name/connection-info specification file
example_topology - example link topology specification file

Jar build instructions:
	(included for documenting purposes, but please test using our pre-built jar!)
compile all .java files in tree of src/
place compiled .class files into corresponding tree of bin/
create jar of overall project e.g. using Eclipse

Setup instructions:

place bank_1.jar in current working directory
place names file and topology file in current directory
	(examples of the format are given in example_names and example_topology)

File formats:

names file is lines of the format "node_name\tnode_host\tnode_port"
topology file is lines of "node_from\tnode_to"
where \t is tab
Please see examples included.

How to run:

specify the jar or bin/ directory on the classpath explicitly in command or implicitly through environment
run java on the corresponding class file

main class for the branch server is: cs5414.bank.network.BranchServer
and it takes args: server_name names_file topology_file
example:
java -cp bin/ cs5414.bank.network.BranchServer aa_server example_names example_topology
-or-
java -cp bank_1.jar cs5414.bank.network.BranchServer aa_server example_names example_topology

main class for the GUI is: 
and it takes args: 
example: 

Run-through instructions:

Use names and topology files provided as examples or write new ones.
Start an instance of BranchServer per server specified in the topology
Start at least one instance of the GUI corresponding to some GUI-node specified in the topology
