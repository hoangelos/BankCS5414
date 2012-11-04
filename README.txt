We used our own Phase I code as a base for Phase II.
We performed a significant partial redesign and rewrite of our network layer and branch server logic. In particular, we fixed our messaging to have correct "fire and forget" semantics and fixed our handling of transfers.

Included files:
---------------

bank_1/ - main project directory
bank_1/src/ - source files
bank_1/bin/ - compiled classes; can run from here if desired
bank_1.jar - pre-built jar; please test using this
bank_1/res/ - example name files and topology files

HowToRunTests.txt - description of procedure for running our unit tests
TestPlan.txt - explanation of test strategy
TOPOexp.txt - topology requirements

Jar build instructions:
-----------------------
	(included for documenting purposes, but please test using our pre-built jar!)

(optional) load bank_1/ into an IDE/editor e.g. Eclipse
compile all .java files in tree of src/
place compiled .class files into corresponding tree of bin/
create jar of overall project e.g. using Eclipse

Setup instructions:
-------------------

extract archive
change to bank_1/ as current working directory
verify bank_1.jar is in current working directory
verify src/ and bin/ are in current working directory and contain source and classes, respectively
verify name files and topology files are in res/ subdirectory of current directory

File formats:
-------------

names file is lines of the format "node_name\tnode_host\tnode_port"
topology file is lines of "node_from\tnode_to"
minus quotes, substitute \t with tab-character, PLEASE NO TRAILING NEWLINE
Please see examples included.

How to run:
-----------


BELOW IS FOR INFORMATION PURPOSES ONLY. IT"S RECOMMENDED THAT YOU FOLLOW THE INSTRUCTIONS
IN HowToRunTests.txt TO SEE THINGS.

specify the jar or bin/ directory on the classpath explicitly in command or implicitly through environment
run java on the corresponding class file

To Run a Single BranchServer:
-----------------------------
main class for the branch server is: cs5414.bank.network.BranchServer
and it takes args: server_name names_file topology_file
example:
java -cp bin/ cs5414.bank.network.BranchServer aa_server res/three_one_names res/three_one_topo
-or-
java -cp bank_1.jar cs5414.bank.network.BranchServer aa_server res/three_one_names res/three_one_topo


To Run a Single GUI:
--------------------
main class for the GUI is:  cs5414.bank.branch.gui.BranchGUI
and it takes args: gui_name branch_name names_file topology_file
example: 
java -cp bin/ cs5414.bank.gui.branch.BranchGUI aa_gui aa_server res/three_one_names res/three_one_topo
-or-
java -cp bank_1.jar cs5414.bank.gui.branch.BranchGUI aa_gui aa_server res/three_one_names res/three_one_topo

Run-through instructions:
-------------------------

Use names and topology files provided as examples or write new ones.
Start an instance of BranchServer per server specified in the topology
Start at least one instance of the GUI corresponding to some GUI-node specified in the topology
