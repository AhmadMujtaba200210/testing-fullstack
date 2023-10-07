'use client'

import {
  Heading,
  Avatar,
  Box,
  Center,
  Image,
  Flex,
  Text,
  Stack,
  Tag,
  useColorModeValue,
} from '@chakra-ui/react'
import DeleteAlert from './DeleteAlert';

import UpdateCustomerDrawer from './UpdateCustomerDrawer';


export default function CardWithImage({id,name,age,email,gender,imageNo,fetchCustomers}) {
  const randomUserGender = gender==="MALE"?"men":"women";
  return (
    <Center py={6}>
      <Box
        maxW={'300px'}
        w={'full'}
        bg={useColorModeValue('white', 'gray.800')}
        boxShadow={'2xl'}
        rounded={'md'}
        overflow={'hidden'}>
        <Image
          h={'120px'}
          w={'full'}
          src={
            'https://images.unsplash.com/photo-1612865547334-09cb8cb455da?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80'
          }
          objectFit="cover"
          alt="#"
        />
        <Flex justify={'center'} mt={-12}>
          <Avatar
            size={'xl'}
            src={
              `https://randomuser.me/api/portraits/${randomUserGender}/${imageNo}.jpg`
            }
            css={{
              border: '2px solid white',
            }}
          />
        </Flex>

        <Box p={6}>
          <Stack spacing={2} align={'center'} mb={5}>
            <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
             {name} <Tag borderRadius={"full"}>{id}</Tag>
            </Heading>
            <Text color={'gray.500'}>{email}</Text>
            <Text color={'gray.500'}>Age {age} | {gender}</Text>

            <UpdateCustomerDrawer
              fetchCustomers={fetchCustomers}
              initialValues={{name,email,age}}
              customerEmail={email}
            />
            <DeleteAlert 
                email={email}
            />
          </Stack>
        </Box>
      </Box>
    </Center>
  )
}