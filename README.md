# 1_year_project
Before you run the program, remember to change the dbName, serverAddress, userName and password in DBConnection class. (db > DBConnection)

**Domain model**

![image](https://github.com/Yi-Chen-Lin2019/1_year_project/blob/master/Domain_model.png)

Here we elaborate the domain model by introducing the patterns we have used: 
1. Composite aggregation:  
 - Bike-RepairList, RepairList-Repair  
 Every Bike has a checklist RepairList and it cannot exist without Bike. RepairList consists of notes and status isFinished. RepairList creates Repair based on the gear type of the bike. If the Bike has external gear type, the RepairList only create external gear Repair in gear section. While if it is internal gear type, the repairlist only create internal gear Repair in gear section. Hence, Repair cannot exist without RepairList.  
When the same bike returns to the shop (the same serial number), user has to register the bike again to get a new RepairList. We implement it this way since our client would like to have a new RepairList to put in new conditions.  
Our client decides the price depending on the condition of the bike and add the total used parts price to final price. Sale price is the price that the bike was sold for. It is not relevant to the man-hours since most of the workers there are volunteers and it will not be the same people or same number of them everyday.  
 
 - RepairItem-Category, Part-Category   
 Repair and Part cannot exist without Category. In Repair, our client would like to have a visualization of categories (see appendices A). For instance, category gear is in red and green and category wheel is in pink. Therefore, we abstract it as Category, and it can be used in Part and Bike. Each Repair and Part has only one Category, while Category can associate with one to many Repair and Part. “isDisable” in Category and RepairItem, “isAvailable” in Part are the flags imply whether they are still using or available.   
  
2. Relation pattern: 
 - Bike-UsedPart, UsedPart-Part  
 UsedPart cannot exist without Bike and Part. Used part has one Part and attribute “isNew”. “isNew” in UsedPart implies whether a new or used part is used for replacement. If the user choses a new part to replace with bike’s broken one (isNew = true), the new price of the Part will be added to the “TotalPartPrice” in Bike. While if the user choses a used one to replace with (isNew = false), the used price of the Part will be added to the bike.   
 
 
3. Item-descriptor patttern: 
 RepairItem is the descriptor of Repair. Repair has an attribute RepairItem to get the repair name. For instance, there is a checkbox named “Internal gear shifter checked”. “Internal gear shifter checked” is the attribute “Name” in RepairItem and its Category is “Internal gear”. While updating the repairing process, the user changes the “Status” in Repair. 
 
 

4. Association: 
 Part can be new or used. New parts are bought from supplier, while used parts come from other bikes which may be too broken to fix but their parts are worth keeping. When repairing a bike, user can choose which type of part he/she wants to replace with in GUI. Since our client does not keep track and quantity of parts and did not express a need for such feature, we do not apply item-descriptor pattern here.  
 
 


**Relational model**



1NF:  
The domain of each attribute must only contain atomic values. Every table in our relational model follow it. And multi-values and repeating groups do not occur.  
When all tables follow 1NF, we move on to 2NF. 
  
2NF: Eliminate partial dependency. 
Admin: Non-prime attribute Password only depends on primary key AdminId. 
Bike: Non-prime attributes SerialNumber, BikeName, RegisterDate, SoldDate, Gender, TotalPartPrice and FinalPrice, SalePrice, isExternalGear and RepairListId only depend on primary key BikeId.  
Since our client would like to create a new list when the same bike (the same serial number) coming back and the information of the bike would be very different (date and price), we make Id as identifier instead of serial number. For instance, when the bike returned, user can follow the same steps as before to register the bike. And in database, we make Id as primary key to identify them. If our client wants to see the repair history with the same serial number, we can show them with timestamp of register date. 
RepairList: Non-prime attributes Note, Status and isFinished only depend on primary key RepairListId.  
Repair: Non-prime attributes Status, RepairItemId and RepairListId only depend on primary key RepairId. Repair needs RepairItem, therefore Repair has a foreign key point to RepairItemId. Repair to RepairList is many to one relation, therefore foreign key RepairListId is stored in many side “Repair”. 
Category: Non-prime attributes CategoryName, Colour and isDisabled only depend on primary key CategoryId. 
RepairItem: Non-prime attributes RepairName CategoryId and isDisabled only depend on primary key RepairItemId. RepairItem needs Category therefore has a foreign key point to CategoryId. 
UsedPart: Non-prime attributes isNew, BikeId, PartId only depend on primary key UsedPartId. UsedPart to Bike is many to one relation, therefore foreign key BikeId is stored in many side “UsedPart”. UsedPart to Part is many to one relation, therefore foreign key PartId is stored in many side “UsedPart”. 
Part: Non-prime attributes Name, UsedPrice, NewPrice, CategoryId and isAvailable depen on primary key PartId. Since our client show no interest in keeping track of stock we are not including it. Part needs Category therefore Part has a foreign key point to CategoryId. 
When all tables follow 1NF, we move on to 3NF. 
  
3NF: Eliminate transitive dependencies. Every table in our relational model follow it. 
Bike: Since our client would like to put different names for every bike including returned bikes. And the BikeName is decided randomly depending on the user. Therefore, BikeName do not transitively depend on SerialNumber, it only depends on BikeId. 
