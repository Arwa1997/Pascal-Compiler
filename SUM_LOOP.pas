PROGRAM SUMPROG
VAR
  N,SUM,I
BEGIN 
READ(N);
SUM:= 0;
FOR I:= 1 TO N DO 
   BEGIN 
     SUM := SUM + I;
   END 
WRITE(SUM)
END. 
 

