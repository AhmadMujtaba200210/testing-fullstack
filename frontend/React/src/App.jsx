import { Wrap,WrapItem,Spinner,Text } from '@chakra-ui/react'
import SidebarWithHeader from './components/shared/SideBar';
import { useEffect,useState } from 'react';
import { getCustomers } from './services/client';
import CardWithImage from './components/card';
import DrawerForm from './components/CreateCustomerDrawer';

const App = () => {

  const [customers,setCustomers] = useState([]);
  const [loading,setLoading] = useState(false);

  const fetchCustomers=()=>{
    setLoading(true);
   setTimeout(()=>{
    getCustomers().then(res=>{
      setCustomers(res.data);
      console.log(res.data);
    }).catch(err=>{
      console.log(err);
    }
    ).finally(()=>setLoading(false))
   },3000)
  }

  useEffect(() => {
    fetchCustomers();
  },[])

  if(loading){
    return(
    <SidebarWithHeader>
      
    </SidebarWithHeader>
    )
  }

  if(customers.length<=0){
    return(
    <SidebarWithHeader>
      <DrawerForm
        fetchCustomers={fetchCustomers}
      />
      <p>No customers found</p>
    </SidebarWithHeader>
    )
  }


  return (
    <SidebarWithHeader>
      <DrawerForm
        fetchCustomers={fetchCustomers}
      />
     
      <Wrap justify={"center"} spacing={"30px"}>
        
      {
        customers.map((customer,index)=>
        <WrapItem key={index}>
          <CardWithImage
           {...customer} 
           imageNo={index}
           fetchCustomers={fetchCustomers}
          />
        </WrapItem>

        )
      }
      </Wrap>
      
    </SidebarWithHeader>
  )
}

export default App;