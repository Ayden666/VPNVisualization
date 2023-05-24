package com.example.vpnvisualization.data.local

import com.example.vpnvisualization.R
import com.example.vpnvisualization.model.AppNode
import com.example.vpnvisualization.model.Connection
import com.example.vpnvisualization.model.ConnectionItem
import com.example.vpnvisualization.model.Destination
import com.example.vpnvisualization.model.DestinationNode
import com.example.vpnvisualization.model.FlowDirection

object LocalConnectionsDataProvider {

    val appNodes = mutableListOf(
        AppNode(
            name = R.string.twitter,
            logo = R.drawable.twitter_logo,
            flowWeight = 1.5f
        ),
        AppNode(
            name = R.string.chrome,
            logo = R.drawable.chrome_logo,
            flowWeight = 3f
        ),
        AppNode(
            name = R.string.youtube,
            logo = R.drawable.youtube_logo,
            flowWeight = 2f
        )
    )

    val connections = mutableListOf(
        Connection(
            isVPN = true,
            nickname = "MyVPNOne",
            ip = "255.255.255.255",
            location = "United States"
        ),
        Connection(
            isVPN = true,
            nickname = "MyVPNTwo",
            ip = "0.0.0.0",
            location = "Spain"
        ),
        Connection(
            isVPN = false,
            nickname = "",
            ip = "",
            location = ""
        )
    )

    val destinationListOne = mutableListOf(
        Destination(
            domain = "www.twitter.com",
            ip = "104.244.42.1",
            direction = FlowDirection.Both
        ),
        Destination(
            domain = "www.substack.com",
            ip = "172.64.154.11",
            direction = FlowDirection.In
        ),
        Destination(
            domain = "www.nytimes.com",
            ip = "151.101.193.164",
            direction = FlowDirection.Out
        )
    )

    val destinationListTwo = mutableListOf(
        Destination(
            domain = "www.google.com",
            ip = "142.251.39.110",
            direction = FlowDirection.Both
        ),
        Destination(
            domain = "www.amazon.com",
            ip = "52.94.236.248",
            direction = FlowDirection.In
        ),
        Destination(
            domain = "www.washingtonpost.com",
            ip = "23.45.108.250",
            direction = FlowDirection.In
        ),
        Destination(
            domain = "www.wikipedia.org",
            ip = "91.198.174.192",
            direction = FlowDirection.In
        ),
        Destination(
            domain = "www.bing.com",
            ip = "13.107.21.200",
            direction = FlowDirection.Out
        ),
    )

    val destinationListThree = mutableListOf(
        Destination(
            domain = "www.youtube.com",
            ip = "142.250.179.206",
            direction = FlowDirection.Both
        ),
        Destination(
            domain = "www.pinterest.com",
            ip = "151.101.192.84",
            direction = FlowDirection.Out
        ),
        Destination(
            domain = "www.reddit.com",
            ip = "151.101.193.140",
            direction = FlowDirection.In
        )
    )

    val destinationNodes = mutableListOf(
        DestinationNode(
            destinations = destinationListOne
        ),
        DestinationNode(
            destinations = destinationListTwo
        ),
        DestinationNode(
            destinations = destinationListThree
        )
    )

    val connectionItems = mutableListOf(
        ConnectionItem(
            appNode = appNodes[0],
            connection = connections[0],
            destinationNode = destinationNodes[0],
            id = 0
        ),
        ConnectionItem(
            appNode = appNodes[1],
            connection = connections[1],
            destinationNode = destinationNodes[1],
            id = 1
        ),
        ConnectionItem(
            appNode = appNodes[2],
            connection = connections[2],
            destinationNode = destinationNodes[2],
            id = 2
        )
    )

    val starredDestinations = mutableSetOf(
        "www.twitter.com",
    )
}