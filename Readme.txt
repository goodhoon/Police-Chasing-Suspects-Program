***** RUN MainB TO SEE THE GUI PANEL*****


<Introduction Police Chasing Suspects Program>

An application that simulates police units picking up a police dog, attending crime scenes, picking up suspects, and dropping them off at a police station within the city of Parallelopolis.

It reads the “police.csv” and “suspects.csv”, runs for a set period of time, then outputs the updated information to “police-output.csv” and “suspects-output.csv”.

Every second, the police units will update their status and position. Their actions will be determined based on their current status. The explanation for each police unit status is shown below:

‘Standby’: Check if there are any suspects to pick up. Assign the closest unassigned suspect to this police unit. Change the police unit status to ‘Approaching Kennel’. If there are no available suspects, do nothing.

‘Approaching Kennel’: Move the police unit towards the Kennel by 3 moves (see
restrictions). If the police unit reaches the Kennel, change the status to ‘At Kennel’

‘At Kennel’: If the police unit is collecting a police dog, remove one dog from the kennel and assign it to the police unit then change the status to ‘Approaching Suspect’. If the police unit is returning a police dog, unassign it from the police unit, return it to the kennel and change status to ‘Returning’.

‘Approaching Suspect’: Move the police unit towards the assigned suspect by 4 moves. If the police unit reaches the suspect, change the status to ‘At Scene’

‘At Scene’: If the police unit has been at the scene for four seconds, change the status to ‘Approaching Kennel’. Otherwise do nothing.

‘Returning’: Move the police unit towards the nearest available station (see restrictions) by 3 moves. If the police unit reaches the station, change the status to ‘Standby’

Some of the police unit actions also change the status of their assigned suspect: 
- When a police unit is assigned a suspect, the suspect’s status is changed to ‘Assigned’
- When the police unit reaches the suspect, the suspect’s status is changed to ‘Caught’
- When the police unit reaches the station, with the caught suspect, the suspect’s status is changed to ‘Jailed’. The suspect is then unassigned from the police unit

- The closest distance is defined as the Euclidean (straight-line) distance between two points.
