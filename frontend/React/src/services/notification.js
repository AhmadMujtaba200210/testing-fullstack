
import { createStandaloneToast } from '@chakra-ui/react'

const { toast } = createStandaloneToast()

const notification = (title,description,status) => {
    
    toast({
        title: title,
        description: description,
        status: status,
        duration: 3000,
        isClosable: true,
    })
}

 const  successNotification=(title,description)=>{
    notification(title,description,"success")
}

const  errorNotification=(title,description)=>{
    notification(title,description,"error")
}

export default 
{
    successNotification,
    errorNotification
}