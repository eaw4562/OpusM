package com.example.opusm.datasource

import com.example.opusm.model.Network

object FakeDataSource {

    val listOfNetwork = listOf(

        Network(
            networkName = "이더리움 메인넷",
            imageLink = "#009000"
        ),
        Network(
            networkName = "Ropsten 테스트 네트워크",
            imageLink = "#cc0000"
        ),
        Network(
            networkName = "Kovan 테스트 네트워크",
            imageLink = "#7d1452"
        ),
        Network(
            networkName = "Rinkeby 테스트 네트워크",
            imageLink = "#ffc000"
        ),
        Network(
            networkName = "Goerli 테스트 네트워크",
            imageLink = "#0092ff"
        ),
        Network(
            networkName = "Localhost 8080",
            imageLink = "#999999"
        )
    )
}