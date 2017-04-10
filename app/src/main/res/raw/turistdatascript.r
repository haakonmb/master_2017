

library(data.table)
library(plyr)
data <- read.csv("turistdataalderbinlandbin.csv")


names <- c("formal","landbin","kjønn","alderbin","utdanning","status")

names.values <- vector("list",6)

for(num in c(1:6)){
  names.values[num] <- na.omit(unique(data[names[num]]))
  names.values[num] <- length(names.values[[num]])
}

probability.independent <- vector("list",11)
probability.dependent <- vector("list",275)
counter <- 1

for(name in c(1:length(names))){
  for(values in c(1:names.values[[name]])){
    for(column in c(7:17)){
      
       tmp <-              table(data[which(data[names[name]] == values ),column])
       tmp <- tmp[1] / sum(tmp)
      probability.dependent[[counter]] <- c(name,values,column,tmp)
      counter <- counter +1
     print(c(names[name],c(values,'/',names.values[[name]]),column,tmp))
    }
  }      
}

counter <- 1
for(column in c(7:17)){
  tmp <-              table(data[,column])
  tmp <- tmp[1] / sum(tmp)
  probability.independent[counter] <- tmp
  counter <- counter +1
}



write.csv(probability.independent,"probability.apriori.csv")
write.csv(probability.dependent,"probability.postpriori.csv")
