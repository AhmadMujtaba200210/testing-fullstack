import { Wrap,WrapItem,Spinner,Text } from '@chakra-ui/react'
import SidebarWithHeader from './components/shared/SideBar';
import { useEffect,useState } from 'react';
import { getCustomers } from './services/client';
import CardWithImage from './components/card';

const App = () => {

  const [customers,setCustomers] = useState([]);
  const [loading,setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
   setTimeout(()=>{
    getCustomers().then(res=>{
      setCustomers(res.data);
      console.log(res.data);
    }).catch(console.error()
    ).finally(()=>setLoading(false))
   },3000)
  },[])

  if(loading){
    return(
    <SidebarWithHeader>
      <Spinner
        thickness='4px'
        speed='0.65s'
        emptyColor='gray.200'
        color='blue.500'
        size='xl'
      />
    </SidebarWithHeader>
    )
  }

  if(customers.length<=0){
    return(
    <SidebarWithHeader>
      <p>No customers found</p>
    </SidebarWithHeader>
    )
  }
  return (
    <SidebarWithHeader>
     
      <Wrap justify={"center"} spacing={"30px"}>
        
      {
        customers.map((customer,index)=>
        <WrapItem key={index}>
          <CardWithImage {...customer} />
        </WrapItem>

        )
      }
      </Wrap>
      
    </SidebarWithHeader>
  )
}

export default App;