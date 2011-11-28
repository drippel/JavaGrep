$price = "3.00000000028822" ;
$price =~ s/(\.\d\d[1-9]?)\d*/$1/;
print $price ;
