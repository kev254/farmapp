<?php 
 
 require_once 'connection.php';


 $phone = $_POST['phone']; 
 $password = md5($_POST['password']);
 $role = "farmer"; 
 $name = $_POST['name']; 
 $quantity_rem = $_POST['quantity_rem'];
 $cost = $_POST['cost'];
 $farmer_id = $_POST['farmer_id'];
 $supplier_id = $_POST['supplier_id'];
 $quantity_used = md5($_POST['quantity_used']);
 
 
 $response = array();
 
 if(isset($_GET['apicall'])){
 
 switch($_GET['apicall']){
 
 case 'signup':
 if(isTheseParametersAvailable(array('phone','password'))){
 
 $stmt = $conn->prepare("SELECT phone FROM users WHERE phone = ?");
 $stmt->bind_param("s", $phone);
 $stmt->execute();
 $stmt->store_result();
 
 if($stmt->num_rows > 0){
 $response['error'] = true;
 $response['message'] = 'Farmer exist !';
 $stmt->close();
 }else{
 $stmt = $conn->prepare("INSERT INTO users (phone, password, role) VALUES ( ?,?,?)");
 $stmt->bind_param("sss", $phone, $password, $role);
 
 if($stmt->execute()){
 $stmt = $conn->prepare("SELECT id, phone, password, role FROM users WHERE phone = ?"); 
 $stmt->bind_param("s",$phone);
 $stmt->execute();
 $stmt->bind_result($id, $phone, $password, $role);
 $stmt->fetch();
 
 
 $farmer = array(
 'id'=>$id, 
 'phone'=>$phone,
 'role'=>$role
 );
 
 $stmt->close();
 
 $response['error'] = false; 
 $response['message'] = 'farmer account created'; 
 $response['farmer'] = $farmer;


 }
 }
 
 }else{
 $response['error'] = true; 
 $response['message'] = 'required parameters are not available'; 
 }
 
 break;
 
 case 'apply_fertilizer':
    
    $stmt = $conn->prepare("INSERT INTO fertilizer_usage ( name, quantity_used,quantity_rem,cost,farmer_id,supplier_id) VALUES ( ?,?,?,?,?,?)");
    $stmt->bind_param("ssssss", $name, $quantity_used, $quantity_rem,$cost,$farmer_id,$supplier_id);
 
 
 if($stmt->execute()){
     
 $response="Fertilizer usage recorded";


 }
 
 break;
 
 case 'login':
     if($_SERVER['REQUEST_METHOD']=='POST'){
        $phone = $_POST['phone'];
        $password = md5($_POST['password']); 
    
        $Sql_Query = "select * from users WHERE phone = '$phone' and password = '$password' ";
    
        $check = mysqli_fetch_array(mysqli_query($conn,$Sql_Query));
        $id=$check['id'];
        $phone=$check['phone'];
        $role=$check['role'];
        $created_at=$check['created_at'];

    
        if(isset($check)){
     
    $farmer = array(
        'id'=>$id, 
        'phone'=>$phone,
        'role'=>$role, 
        'created_at'=>$created_at);

    $response['farmer'] = $farmer; 
    
    }
    else {
    echo "Invalid Username or Password Please Try Again ";
    echo $username;
    }
    } else {
    echo "Check Again";
    }
    
    break;
 

 
 default: 
 $response['error'] = true; 
 $response['message'] = 'Invalid Operation Called';
 }
 
 }else{
 $response['error'] = true; 
 $response['message'] = 'Invalid API Call';
 }
 
 echo json_encode($response);
 
 function isTheseParametersAvailable($params){
 
 foreach($params as $param){
 if(!isset($_POST[$param])){
 return false; 
 }
 }
 return true; 
 }
 

?>