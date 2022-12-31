<?php 
 
 $farmerid=$_GET['farmerId'];
 require_once 'connection.php';

 
 //creating a query
 $stmt = $conn->prepare("select * from fertilizer_usage WHERE farmer_id=? ORDER BY id DESC");
 $stmt->bind_param("s",$farmerid);
 //executing the query 
 $stmt->execute();
 
 //binding results to the query
 $stmt->bind_result($id,$name,$quantity_used,$quantity_rem,$cost,$farmer_id,$supplier_id);
 
 

 $usageArray = array(); 
 
 //traversing through all the result 
 while($stmt->fetch()){
 $temp = array();
 $temp['id'] = $id; 
 $temp['name'] = $name; 
 $temp['quantity_used'] = $quantity_used;
 $temp['quantity_rem'] = $quantity_rem;
 $temp['supplier_id'] = $supplier_id;
 

 array_push($usageArray, $temp);
 }
 
 //debuging error log in log file
 error_log(json_encode($usageArray),3,"error.log");
  
 //displaying the result in json format 

 header('Content-Type: application/json');
 echo json_encode($usageArray);
 
 
?>