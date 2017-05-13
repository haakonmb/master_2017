


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

#Getting the tabled distribution of data on the values to find the optimal candidate
values<- vector("list",6)
counter <- 1
for(name in names){
  tmp <- table(data[name])
  values[[counter]] <- tmp
  counter <- counter +1
  
}

labels <- vector("list",6)

labels[[1]] <- c("Visiting friends", "Other vacation and freetime-activities")
labels[[2]] <- c("Norway","Europe","America","Asia","Other")
labels[[3]] <- c("Male","Female","Other")
labels[[4]] <- c("1-29","30-45","46-60","60+")
labels[[5]] <- c("Lower Secondary","Higher Secondary","Bachelor","Master","Researcher","Dont know")
labels[[6]] <- c("Employed","Self-employed","Pensioner","Student","Other","Dont know")

questions <- vector("list",6)
questions[[1]] <- "Main Purpose of visit?"
questions[[2]] <- "Country/continent of permanent residence?"
questions[[3]] <- "Gender?"
questions[[4]] <- "Age-range?"
questions[[5]] <- "Highest education level?"
questions[[6]] <- "Status?"

percent <- vector("list",6)
counter <-1

for(i in c(1:6)){
  percent[[counter]] <- round(values[[counter]]/sum(values[[counter]])*100)
  labels[[counter]] <- paste(labels[[counter]],percent[[counter]])
  labels[[counter]] <- paste(labels[[counter]],"%",sep="")
 # pie(values[[counter]],labels=labels[[counter]],col=rainbow(length(labels[[counter]])),main = questions[[counter]])
 plot(percent[[counter]],ylim=range(1:100),type="h")
  counter <- counter +1
}


#viktor sin plot
library(ggplot2)
a <-c(percent[[1]][[1]],percent[[1]][[2]])
a <- c(percent[[2]][[1]],percent[[2]][[2]],percent[[2]][[3]],percent[[2]][[4]],percent[[2]][[5]])
a <- c(percent[[3]][[1]],percent[[3]][[2]])
a <- c(percent[[4]][[1]],percent[[4]][[2]],percent[[4]][[3]],percent[[4]][[4]])
a <- c(percent[[5]][[1]],percent[[5]][[2]],percent[[5]][[3]],percent[[5]][[4]],percent[[5]][[5]],percent[[5]][[6]])
a <- c(percent[[6]][[1]],percent[[6]][[2]],percent[[6]][[3]],percent[[6]][[4]],percent[[6]][[5]],percent[[6]][[6]])


b <- c("Visiting friends", "Other vacation activities")
b <- c("Norway","Europe","America","Asia","Other")
b <- c("Male","Female")
b <- c("1-29","30-45","46-60","60+")
b <- c("Lower Secondary","Higher Secondary","Bachelor","Master","Researcher","Dont know")
b <- as.character(b)
b <- factor(b, levels=unique(b))


b <- c("Employed","Self-employed","Pensioner","Student","Other","Dont know")
df <- data.frame(a,b)
df

ggplot(data = df,aes(b, weight = a)) +  
  geom_bar(aes(fill = b)) +  
  theme_bw() +  
  scale_fill_brewer(palette = "Blues") +   
  xlab("Features") +  
  ylab("% of .....") +  
  coord_cartesian(ylim = c(0,100)) +
                    theme(text = element_text(size=25),
                          axis.text.x = element_text(angle=90, hjust=1)) 


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

#df.values <- data.frame(matrix(unlist(values), nrow = 25,byrow=T))

df.prob.pre  <- data.frame(matrix(unlist(probability.independent), nrow=11, byrow=T))
df.prob.post <- data.frame(matrix(unlist(probability.dependent), nrow=25, byrow=T))

write.csv(df.prob.pre,"probability_apriori.csv",row.names=FALSE)
write.csv(df.prob.post,"probability_postpriori.csv",row.names=FALSE)
