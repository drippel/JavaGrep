$line = "The name \"McDonald's\" is said \"makudonarudo\" in Japanese";
$line =~ /".*"/$1/;
print $line 
