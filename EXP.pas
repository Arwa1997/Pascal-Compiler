PROGRAM EEXP 
VAR
  a,b,left,right , result
BEGIN 
READ(a,b);
WRITE(a,b); 
left:=(a+b)+left; 
right:=right+(a+b); 
result:=left*right;
WRITE(result)
END. 
