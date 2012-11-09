Because we had problems with our Phase I code, we used our Phase II code (which was a partial rewrite) as a base for this.
We removed the snapshot logic from the branch server, etc, leaving behind approximately a Phase I.

Included files:
---------------

bank_1/ - main project directory
bank_1/src/ - source files
bank_1/bin/ - compiled classes; can run from here if desired
bank_1.jar - pre-built jar; please test using this
bank_1/res/ - example name files and topology files

HowToRunTests.txt - description of procedure for running our unit tests
TestPlan.txt - explanation of test strategy
LOGIC.txt - explanation of strategy for replication

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
branches file is lines of "branch_name\tcomma_separated_list"
	where comma_separated_list is a list of branch names separated by commas
minus quotes, substitute \t with tab-character, PLEASE NO TRAILING NEWLINE
Please see examples included.

How to run:
-----------

specify the jar or bin/ directory on the classpath explicitly in command with -cp flag
	or implicitly through environment
run java on the corresponding class file

To Run a Single BranchServer:
-----------------------------
main class for the branch server is: cs5414.bank.network.BranchServer
and it takes args: server_name names_file topology_file branches_file
example:
java -cp bin/ cs5414.bank.network.BranchServer aa_server res/chain_repl_names res/chain_repl_topo res/chain_repl_branches
-or-
java -cp bank_1.jar cs5414.bank.network.BranchServer [same args as above]


To Run a Single GUI:
--------------------
main class for the GUI is:  cs5414.bank.branch.gui.BranchGUI
and it takes args: gui_name branch_name names_file topology_file
example: 
java -cp bin/ cs5414.bank.gui.branch.BranchGUI aa_gui aa_server res/chain_repl_names res/chain_repl_topo
-or-
java -cp bank_1.jar cs5414.bank.gui.branch.BranchGUI aa_gui aa_server res/chain_repl_names res/chain_repl_topo

To run failure detector GUI:
----------------------------

java -cp bin/ cs5414.bank.gui.faildetect.FailGUI fd_gui res/replica_names res/replica_topo

Run-through instructions:
-------------------------

Use names and topology files provided as examples or write new ones.
Start an instance of BranchServer per server specified in the topology
Start at least one instance of the GUI corresponding to some GUI-node specified in the topology
