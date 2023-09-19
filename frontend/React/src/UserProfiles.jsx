import React from 'react'

 const UserProfiles = ({name,age,gender,image, ...props}) => {

    gender= gender==="MALE"?"men":"women"
  return (    
    
    <div>
        <p>{name}</p>
        <p>{age}</p>
        <p>{props.children}</p>
        <img src={`https://randomuser.me/api/portraits/${gender}/${image}.jpg`}/>
    </div>
  )
}

export default UserProfiles