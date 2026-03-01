#Example of procedure(execution) on vk script language

var domain = Args.domain;
var query = Args.query;
var token = "my_token";

var first_response = API.wall.search({
"access_token": token,
"query": query,
"domain": domain,
"count": 100,
"offset": 0
});

var offset = 0;
var requests = 1;

if (first_response.count < 100) {
return {
"count": first_response.count,
"requests": requests,
"offset": 0,
"message": ""
};
}

var count = 100;
offset = 100;
var remaining = 24;

while (remaining != 0) {
var response = API.wall.search({
"access_token": token,
"query": query,
"domain": domain,
"count": 100,
"offset": offset
});

count = count + response.count;
requests = requests + 1;

if (response.count < 100) {
return {
"count": count,
"requests": requests,
"offset": offset,
"message": ""
};
}

offset = offset + 100;
remaining = remaining - 1;

}

return {
"count": count,
"requests": requests,
"offset": offset,
"message": "api limit reached"
};