# Sorted Pokemons Microservice

## Application Diagram

![Diagram](pokemon-diagram.drawio.svg)

### Routes 🚲

GET POKEMONS: `/pokemons`<br>
GET POKEMON HIGHLIGHTS: `/pokemons/highlight`<br>

>[!NOTE]<br>
>you can set optional parameters for both endpoints, example: `/pokemons?query=krook&sort=LENGHT`.

`query`: sending any part of pokemon name, service will retrieve a list of pokemons.

`sort`: you can fill this param with **LENGHT** to retrieve pokemons ascending, by default is sorted alphabetically.

get pokemons response example: `pokemons?query=kro&sort=LENGTH`
~~~~
{
    "result": [
        "zekrom",
        "murkrow",
        "krokorok",
        "honchkrow",
        "krookodile"
    ]
}
~~~~
get pokemon highlights response example: `pokemons/highlight?query=kroo`
~~~~
{
    "result": [
        {
            "name": "krookodile",
            "highlight": "<pre>kroo</pre>kodile"
        }
    ]
}
~~~~
