# Connect N Player Template

## Primary Goal
* Publish an implementation of Player to artifactory. Become familiar with software workflows and artifact deployment.

## Secondary Goal
* Produce a champion connect N AI.

## Steps
* Fork this repo into a personal group on gitlab.io
* Change the firstname and surname properties in the pom.xml to your name
* Move the class NameAI from the com.thg.accelerator19.connectn.ai.name package to the com.thg.accelerator19.connectn.ai.firstnamesurname package 
* Rename the NameAI class to whatever you would like to call your AI
* Implement your Connect N AI logic inside makeMove


## Rules
* For the competition, we will set the width of the board to 10, height to 8, and n to 4. In the case of a draw between two ais, we will change n to 3, so make sure you don't hard-code these parameters in your code (get them from board.getGameConfig())
* Feel free to copy/improve the analysis code in https://gitlab.io.thehut.local/halls/connect-n/blob/master/src/main/java/com/thehutgroup/accelerator/connectn/analysis/BoardAnalyser.java, although if you do copy it, make sure you change the package so it doesn't conflict with the fully qualified class name in connect n.
* Your solution must use less than 2G of Heap, and terminate within 10 seconds on Shaun's i7 laptop. Your jar should be no larger than 10MB.
* You may calculate a move database, which can be stored inside your jar. However, no calls to internet services are allowed. 
* If your AI makes a foul move or violates the performance constraints above, it will forfeit the game.

## Making your submission
Once you're ready to submit your entry:
* Change the version in the pom.xml from x-SNAPSHOT to x (e.g. x=1.0)
* Run the command ```mvn deploy```
* Check that the artifact appears in https://artifactory.io.thehut.local/artifactory/webapp/#/artifacts/browse/tree/General/libs-release-local/com/thg/accelerator19/connectn/ai/
  * If it isn't there, or your deploy command fails, your maven environment isn't set up correctly. Speak to Shaun.
* Push your code to git to keep a record of the source code for version x
* In your pom.xml, increment x and add -SNAPSHOT at the end (e.g. go from 1.0 to 1.1-SNAPSHOT) to get ready for the next version
* Update the spreadsheet below with this version https://hutgroupnorthwich.sharepoint.com/:x:/s/Accelerator2019/EWD2QHt2cCpKnKLoj8VRmi8BQ6H83sHuyt-P7F4aKvK0BQ?e=gtRusS