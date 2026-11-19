# Experiment 12: Analysis of HTTP and HTTPS Traffic Using Wireshark

## 1. Aim

To capture and analyze HTTP and HTTPS network traffic using Wireshark, and to compare the visibility of data in an unencrypted HTTP connection with the encrypted application data carried by HTTPS.

## 2. Software and Requirements

- macOS
- Wireshark
- A web browser (Google Chrome was used for the demonstrated captures)
- An active network connection

> **Platform note:** This practical was performed on macOS. The active capture interface shown in Wireshark was `en0`. Interface names can differ between computers and network configurations.

## 3. Theory

### HTTP

Hypertext Transfer Protocol (HTTP) is an application-layer protocol used for communication between a web browser and a web server. In a plain HTTP connection, request information such as the request method, path, host, and several headers can be read in a packet capture.

### HTTPS

HTTPS is HTTP communication protected by TLS (Transport Layer Security). During connection setup, the client and server exchange handshake messages to negotiate security parameters and establish keys. After the handshake, application data is protected by encryption. A packet capture can still reveal some connection metadata, packet sizes, and TLS record types, but the protected application payload is not ordinarily readable as plain HTTP.

### Main difference

| Feature | HTTP | HTTPS |
|---|---|---|
| Transport protection | No TLS protection | Protected using TLS |
| Request headers in packet capture | Readable in plain HTTP | Protected inside encrypted application data after the handshake |
| Confidentiality | Not provided by HTTP itself | Provided by TLS encryption |
| Typical Wireshark evidence | HTTP GET and response status | TLS Client Hello, Server Hello, and Application Data |

## 4. Procedure

### Part A: HTTP packet capture

1. Open Wireshark on macOS.
2. Select the active network interface (`en0` in this capture) and start capturing packets.
3. Open a browser and visit `http://neverssl.com/`.
4. Allow the page to load and generate traffic.
5. Stop the capture.
6. Enter the display filter `http`.
7. Select the HTTP GET request and inspect the packet details.

**Observation from the supplied capture:**

- Packet 415 contains `GET /online HTTP/1.1`.
- The request includes a `Host` header and other readable HTTP headers, including `User-Agent`, `Accept`, `Referer`, `Connection`, and `Accept-Encoding`.
- Packet 432 shows `301 Moved Permanently`.
- Packet 434 contains `GET /online/ HTTP/1.1`.
- Packet 444 shows `200 OK`.
- Packets 447 and 450 show a favicon request and its successful PNG response.

The HTTP screenshot demonstrates that the captured request and its headers are readable.

### Part B: HTTPS/TLS packet capture

1. Start a new capture on the active network interface.
2. Open a browser and visit `https://www.google.com/`.
3. Allow the page to load and generate traffic.
4. Stop the capture.
5. Enter the display filter `tls`.
6. Locate and inspect TLS handshake and application-data packets.

**Observation from the supplied capture:**

- The packet list contains TLS Client Hello messages, including entries with server names such as `www.gstatic.com`.
- Packet 389 is identified as a Server Hello message.
- The Server Hello details show the cipher suite `TLS_AES_128_GCM_SHA256` and key-exchange group `X25519MLKEM768`.
- Packet 392 is identified as TLS Application Data. Its details show an encrypted application-data payload.
- The TLS packet details display legacy version values such as `0x0303`. These legacy fields do not by themselves mean that the negotiated connection is TLS 1.2. The Server Hello's Supported Versions extension identifies TLS 1.3 in this capture.

The HTTPS screenshots demonstrate the TLS handshake and encrypted application data. The screenshots do not include decrypted HTTP content.

## 5. Screenshot Evidence

The `Screenshots` folder contains the following four captures:

| Screenshot | Evidence |
|---|---|
| `01_HTTP_Request_Headers.png` | HTTP GET request and readable request headers |
| `02_TLS_Encrypted_Application_Data.png` | TLS Application Data record and encrypted payload |
| `03_TLS_Client_Hello.png` | TLS Client Hello handshake details |
| `04_TLS_Server_Hello.png` | TLS Server Hello, cipher suite, and key-share details |

## 6. Result

HTTP and HTTPS traffic was captured and inspected successfully using Wireshark on macOS. The HTTP capture exposed the GET request and its headers in readable form. The HTTPS capture showed TLS handshake messages and encrypted application data, demonstrating how TLS protects application communication.

## 7. Conclusion

The experiment demonstrates the security difference between plain HTTP and HTTPS. HTTP traffic does not provide encryption by itself, so request information can be visible in a packet capture. HTTPS uses TLS to protect application data in transit, while still exposing some handshake and connection metadata.
