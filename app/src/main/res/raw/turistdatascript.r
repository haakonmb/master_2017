


library(plyr)
data <- read.csv("turistdataalderbinlandbin.csv")


names <- c("formal","landbin","kjønn","alderbin","utdanning","status")

names.values <- vector("list",6)

for(num in c(1:6)){
  names.values[num] <- na.omit(unique(data[names[num]]))
  names.values[num] <- length(names.values[[num]])
}

probability.independent <- vector("list",11)
probability.dependent <- vector("list",25)
counter <- 1


for(name in c(1:length(names))){
  for(values in c(1:names.values[[name]])){
    tmp.values <- vector("list",11)
    value.counter <- 1
    for(column in c(7:17)){
      
      tmp <-              table(data[which(data[names[name]] == values ),column])
      tmp <- tmp[1] / sum(tmp)
      
      tmp.values[[value.counter]] <- tmp
      value.counter <- value.counter +1
      
      #probability.dependent[[counter]] <- c(name,values,column,tmp)
#      counter <- counter +1
 #     print(c(names[name],c(values,'/',names.values[[name]]),column,tmp))
    }
    print(tmp.values)
    probability.dependent[[counter]] <- c(name,values,tmp.values[1],tmp.values[2],tmp.values[3],tmp.values[4],tmp.values[5],tmp.values[6],tmp.values[7],tmp.values[8],tmp.values[9],tmp.values[10],tmp.values[11])  
    print(probability.dependent[[counter]])
    counter <- counter +1
  }      
}




#Old evidence-probabilities generator
# for(name in c(1:length(names))){
#   for(values in c(1:names.values[[name]])){
#     for(column in c(7:17)){
#       
#        tmp <-              table(data[which(data[names[name]] == values ),column])
#        tmp <- tmp[1] / sum(tmp)
#       probability.dependent[[counter]] <- c(name,values,column,tmp)
#       counter <- counter +1
#      print(c(names[name],c(values,'/',names.values[[name]]),column,tmp))
#     }
#   }      
# }

counter <- 1
for(column in c(7:17)){
  tmp <-              table(data[,column])
  tmp <- tmp[1] / sum(tmp)
  probability.independent[counter] <- tmp
  counter <- counter +1
}

df.prob.pre  <- data.frame(matrix(unlist(probability.independent), nrow=11, byrow=T))
df.prob.post <- data.frame(matrix(unlist(probability.dependent), nrow=25, byrow=T))

write.csv(df.prob.pre,"probability_apriori.csv",row.names=FALSE)
write.csv(df.prob.post,"probability_postpriori.csv",row.names=FALSE)
