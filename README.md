# 1_year_project
Before you run the program, remember to change the dbName, serverAddress, userName and password in DBConnection class. (db > DBConnection)

Domain model 
![image](https://github.com/Yi-Chen-Lin2019/1_year_project/blob/master/Domain_model.png)
Here we elaborate the domain model by introducing the patterns we have used: 
Composite aggregation:  
Bike-RepairList, RepairList-Repair  
Every Bike has a checklist RepairList and it cannot exist without Bike. RepairList consists of notes and status isFinished. RepairList creates Repair based on the gear type of the bike. If the Bike has external gear type, the RepairList only create external gear Repair in gear section. While if it is internal gear type, the repairlist only create internal gear Repair in gear section. Hence, Repair cannot exist without RepairList.  
When the same bike returns to the shop (the same serial number), user has to register the bike again to get a new RepairList. We implement it this way since our client would like to have a new RepairList to put in new conditions.  
Our client decides the price depending on the condition of the bike and add the total used parts price to final price. Sale price is the price that the bike was sold for. It is not relevant to the man-hours since most of the workers there are volunteers and it will not be the same people or same number of them everyday.  
 
RepairItem-Category, Part-Category   
Repair and Part cannot exist without Category. In Repair, our client would like to have a visualization of categories (see appendices A). For instance, category gear is in red and green and category wheel is in pink. Therefore, we abstract it as Category, and it can be used in Part and Bike. Each Repair and Part has only one Category, while Category can associate with one to many Repair and Part. “isDisable” in Category and RepairItem, “isAvailable” in Part are the flags imply whether they are still using or available.   
  
Relation pattern: 
Bike-UsedPart, UsedPart-Part  
UsedPart cannot exist without Bike and Part. Used part has one Part and attribute “isNew”. “isNew” in UsedPart implies whether a new or used part is used for replacement. If the user choses a new part to replace with bike’s broken one (isNew = true), the new price of the Part will be added to the “TotalPartPrice” in Bike. While if the user choses a used one to replace with (isNew = false), the used price of the Part will be added to the bike.   
 
 Item-descriptor patttern: 
RepairItem is the descriptor of Repair. Repair has an attribute RepairItem to get the repair name. For instance, there is a checkbox named “Internal gear shifter checked”. “Internal gear shifter checked” is the attribute “Name” in RepairItem and its Category is “Internal gear”. While updating the repairing process, the user changes the “Status” in Repair. 
 
Association: 
Part can be new or used. New parts are bought from supplier, while used parts come from other bikes which may be too broken to fix but their parts are worth keeping. When repairing a bike, user can choose which type of part he/she wants to replace with in GUI. Since our client does not keep track and quantity of parts and did not express a need for such feature, we do not apply item-descriptor pattern here.  
 
 
Page Break
 
System Sequence Diagram and Operation Contract 
Use case 
System sequence diagram 
Operation contract 
