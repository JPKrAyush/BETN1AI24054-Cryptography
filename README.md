# BETN1AI24054 — Cryptography Practical Experiments

This repository contains the practical experiments for the Cryptography laboratory. Each experiment has its own folder with a detailed `README.md`, the commands or source code used, and output screenshots.

## List of Experiments

| No. | Experiment (Problem Statement) | Tool Used | Folder |
|----:|--------------------------------|-----------|--------|
| 1 | Implement Caesar, Vigenère, and Rail Fence ciphers and compare their encryption and decryption processes | CrypTool | [Experiment_01_Classical_Ciphers](Experiment_01_Classical_Ciphers) |
| 2 | Perform frequency analysis on classical ciphers and analyze their resistance to cryptanalysis | CrypTool | [Experiment_02_Frequency_Analysis](Experiment_02_Frequency_Analysis) |
| 3 | Compare DES and AES encryption by encrypting sample data and evaluating their security and performance | CrypTool | [Experiment_03_DES_vs_AES](Experiment_03_DES_vs_AES) |
| 4 | Generate RSA public/private key pairs and perform file encryption and decryption | OpenSSL | [Experiment_04_RSA_File_Encryption](Experiment_04_RSA_File_Encryption) |
| 5 | Generate SHA-256 hashes, create digital signatures, and verify signature authenticity | OpenSSL | [Experiment_05_SHA256_Digital_Signature](Experiment_05_SHA256_Digital_Signature) |
| 6 | Create and inspect self-signed X.509 digital certificates and analyze certificate attributes | OpenSSL | [Experiment_06_X509_Certificate](Experiment_06_X509_Certificate) |
| 7 | Implement RSA and Diffie–Hellman algorithms to demonstrate secure key exchange and encryption | VS Code | [Experiment_07_RSA_Diffie_Hellman](Experiment_07_RSA_Diffie_Hellman) |
| 8 | Implement AES encryption and SHA-256 hashing for securing text messages | VS Code | [Experiment_08_AES_SHA256](Experiment_08_AES_SHA256) |
| 9 | Implement password hashing with and without salting and compare resistance against password attacks | VS Code | [Experiment_09_Password_Hashing_Salt](Experiment_09_Password_Hashing_Salt) |
| 10 | Capture HTTPS traffic and identify the TLS handshake, certificates, and cipher suites | Wireshark | [Experiment_10_HTTPS_TLS_Analysis](Experiment_10_HTTPS_TLS_Analysis) |
| 11 | Capture and analyze encrypted VPN traffic and compare it with unencrypted network communication | Wireshark | [Experiment_11_VPN_Traffic_Analysis](Experiment_11_VPN_Traffic_Analysis) |
| 12 | Analyze network packets to identify secure and insecure communication protocols | Wireshark | [Experiment_12_Secure_Insecure_Protocols](Experiment_12_Secure_Insecure_Protocols) |
| 13 | Perform password auditing using John the Ripper and evaluate password strength | Kali Linux | [Experiment_13_Password_Auditing_John](Experiment_13_Password_Auditing_John) |

## Tools Used

- **CrypTool** — classical ciphers, frequency analysis, DES/AES (Experiments 1–3)
- **OpenSSL** — RSA, SHA-256, digital signatures, X.509 certificates (Experiments 4–6)
- **VS Code (Java)** — RSA, Diffie–Hellman, AES, SHA-256, password hashing (Experiments 7–9)
- **Wireshark** — HTTPS/TLS, VPN and protocol traffic analysis (Experiments 10–12)
- **Kali Linux / John the Ripper** — password auditing (Experiment 13)

## How to Use

Open any experiment folder and read its `README.md`. It contains the aim, theory, step-by-step procedure, the commands or source code used, output screenshots, and the result for that experiment.
