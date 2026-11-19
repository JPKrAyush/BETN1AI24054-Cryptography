# Experiment 10: HTTPS/TLS Analysis Using Wireshark

## 1. Aim

To capture and analyze HTTPS traffic using Wireshark and study the TLS (Transport Layer Security) handshake process, negotiated TLS version, cipher suite, and X.509 digital certificate.

## 2. Objectives

- To capture HTTPS traffic using Wireshark.
- To identify and analyze the TLS handshake messages.
- To examine the Client Hello and Server Hello messages.
- To identify the negotiated TLS version and cipher suite.
- To analyze the X.509 digital certificate.
- To understand the role of encryption and digital certificates in secure communication.

## 3. Software and Requirements

- Operating System: macOS
- Wireshark: Network protocol analyzer
- Google Chrome: Web browser
- Internet connection
- TLS key log file for decrypting TLS handshake traffic

## 4. Introduction

HTTPS (Hypertext Transfer Protocol Secure) is a secure version of HTTP that uses TLS (Transport Layer Security) to protect communication between a web browser and a web server.

TLS provides three important security features:

1. **Confidentiality:** Encrypts transmitted data to prevent unauthorized access.
2. **Integrity:** Helps ensure that transmitted data has not been modified.
3. **Authentication:** Uses digital certificates to verify the identity of the server.

Wireshark is a network protocol analyzer that allows us to capture and inspect network packets. It can be used to study TLS handshake messages, encryption protocols, certificates, and other network communication details.

## 5. Theory

### 5.1 TLS Handshake

The TLS handshake is the process through which a client and server establish a secure connection before exchanging application data.

The main handshake messages include:

**1. Client Hello**

The client initiates communication by sending a Client Hello message. It contains:

- Supported TLS versions
- Supported cipher suites
- Client random value
- Extensions
- Server Name Indication (SNI)

**2. Server Hello**

The server responds with a Server Hello message. It contains:

- Selected TLS version
- Selected cipher suite
- Server random value
- Key exchange information

**3. Certificate**

The server sends its digital certificate to establish its identity. The certificate contains information about the subject, issuer, validity period, and public key.

**4. Certificate Verify**

The server provides cryptographic proof that it possesses the private key corresponding to its certificate.

**5. Finished**

Both parties send Finished messages to verify the integrity of the handshake and confirm that the secure connection has been established.

In TLS 1.3, several handshake messages after Server Hello are encrypted. Therefore, a TLS key log file can be configured in Wireshark to decrypt the captured traffic for analysis.

### 5.2 Cipher Suite

A cipher suite defines the cryptographic algorithms used to secure communication.

For example:

`TLS_AES_256_GCM_SHA384`

It uses:

- AES-256 for symmetric encryption.
- GCM for authenticated encryption.
- SHA-384 for the hash function used in the TLS key schedule.

### 5.3 Digital Certificate (X.509)

An X.509 digital certificate is used to associate a public key with an identity.

It contains information such as:

- Certificate version
- Serial number
- Subject
- Issuer
- Validity period
- Public key algorithm
- Signature algorithm

The certificate is digitally signed by a Certificate Authority (CA) to help establish trust.

## 6. Procedure

1. Install and open Wireshark.
2. Configure Google Chrome to generate a TLS key log file.
3. Configure Wireshark to use the generated TLS key log file.
4. Select the active Wi-Fi network interface in Wireshark.
5. Start packet capture.
6. Open Google Chrome and visit `https://www.google.com`.
7. Refresh the webpage to generate HTTPS traffic.
8. Stop the packet capture.
9. Apply the display filter `tls` to identify TLS traffic.
10. Locate and inspect the Client Hello message.
11. Examine the Server Hello message and identify the negotiated TLS version and cipher suite.
12. Inspect the Certificate handshake message to analyze the X.509 certificate.
13. Apply the filter `tcp.stream == 6` to examine the captured TCP connection and its handshake sequence.

## 7. Wireshark Display Filters

| Display Filter | Purpose |
|---|---|
| `tls` | Displays TLS traffic |
| `tls.handshake.type == 1` | Displays Client Hello messages |
| `tls.handshake.type == 2` | Displays Server Hello messages |
| `tls.handshake.type == 11` | Displays Certificate messages |
| `tcp.stream == 6` | Displays packets belonging to TCP stream 6 |

## 8. Observations and Results

### 8.1 Client Hello

The Client Hello message was captured successfully.

Observed details:

- Handshake Type: Client Hello
- Server Name Indication: `www.gstatic.com`
- Supported TLS Versions: TLS 1.3 and TLS 1.2
- Supported Key Shares: X25519MLKEM768 and X25519

### 8.2 Server Hello

The Server Hello message was successfully identified.

Observed details:

- Handshake Type: Server Hello
- Negotiated TLS Version: TLS 1.3
- Selected Cipher Suite: `TLS_AES_256_GCM_SHA384`
- Compression Method: Null
- Key Share: X25519MLKEM768

### 8.3 X.509 Certificate Analysis

The certificate was successfully decrypted and inspected using Wireshark.

Observed certificate details:

| Certificate Field | Observed Value |
|---|---|
| Version | X.509 v3 |
| Serial Number | `0x00d8fe1ed7e9c8ec2d091b8eebf57e62d2` |
| Subject | `*.gstatic.com` |
| Issuer | Google Trust Services (WE2) |
| Valid From | 03 November 2026 |
| Valid Until | 26 January 2027 |
| Public Key Algorithm | Elliptic Curve (EC) |
| Elliptic Curve | secp256r1 |
| Signature Algorithm | ECDSA with SHA-256 |

### 8.4 TLS Handshake Analysis

The captured TCP stream (Stream 6) showed the following handshake sequence:

| Packet Number | Observed Message |
|---|---|
| 250 | Client Hello |
| 262 | Server Hello, Change Cipher Spec |
| 264 | Encrypted Extensions, Certificate, Certificate Verify, Finished |
| 269 | Change Cipher Spec, Encrypted Extensions, Finished |
| 293 | HTTP/2 communication |

The handshake messages were successfully inspected after configuring TLS decryption in Wireshark.

## 9. Screenshots

The following screenshots were captured during the experiment:

1. `Exp10_01_TLS_Traffic.png` – Captured HTTPS/TLS network traffic.
2. `Exp10_02_Client_Hello.png` – Client Hello message and its parameters.
3. `Exp10_03_Server_Hello.png` – Server Hello message and negotiated cipher suite.
4. `Exp10_04_Certificate_Details.png` – X.509 certificate information.
5. `Exp10_05_TLS_Handshake.png` – Complete captured TLS handshake sequence.

## 10. Result

Successfully captured and analyzed HTTPS traffic using Wireshark. Identified the Client Hello and Server Hello messages, verified the negotiated TLS 1.3 version and cipher suite, and examined the X.509 digital certificate, including its subject, issuer, validity period, public key algorithm, and signature algorithm.

The TLS handshake process and the role of digital certificates in establishing secure communication were successfully studied.

## 11. Conclusion

In this experiment, HTTPS traffic was captured and analyzed using Wireshark. The TLS handshake process was studied by examining the Client Hello, Server Hello, Certificate, and Finished messages.

The negotiated TLS version, cipher suite, and X.509 certificate details were successfully identified. This experiment helped in understanding how TLS provides confidentiality, integrity, and authentication during secure network communication.

---

**End of Experiment 10**
