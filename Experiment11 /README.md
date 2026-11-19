# Experiment 11: VPN Traffic Analysis on macOS

## 1. Aim

To configure OpenVPN CloudConnexa on macOS and use Wireshark to capture and compare:
- ordinary HTTP traffic;
- OpenVPN tunnel traffic; and
- HTTP and OpenVPN traffic in a combined capture.

## 2. Environment and tools

- Operating system: macOS
- Computer: MacBook Air M3
- VPN service: OpenVPN CloudConnexa trial
- VPN client: OpenVPN Connect
- Packet analyzer: Wireshark
- Capture interface used: `en0` (physical Wi-Fi interface)
- HTTP test site: `http://neverssl.com`

## 3. CloudConnexa setup

1. Sign in to the CloudConnexa administration portal.
2. Open **Users → Devices** and choose **Add Device**.
3. Create a device named `MacBook-Air-M3` and assign it to the intended user.
4. Download the device profile.
5. Import the profile into OpenVPN Connect on macOS.
6. Connect to the CloudConnexa network.
7. Confirm the connection in OpenVPN Connect. The macOS Network Configuration can also be checked with:

   ```bash
   scutil --nc list
   ```

The tested CloudConnexa endpoint displayed Mumbai. The device was shown as connected during the VPN verification stage.

## 4. Wireshark capture procedure

For these captures, select the physical Wi-Fi interface `en0` in Wireshark. Start the capture before generating the relevant traffic, then stop it and apply the display filter.

### Part A: HTTP baseline capture

1. Disconnect OpenVPN Connect before starting the baseline capture.
2. Start a Wireshark capture on `en0`.
3. Open `http://neverssl.com` in a browser. Use HTTP, not HTTPS.
4. Wait for the page to load, stop the capture, and apply:

   ```text
   http
   ```

5. Inspect the HTTP GET request and the server response.
6. Save the capture as `Captures/Exp11_HTTP_Baseline.pcapng`.

**Observed in the supplied capture:**
- 405 captured packets in total.
- HTTP requests to `coolshininglushlight.neverssl.com`.
- `GET /online` followed by `HTTP/1.1 301 Moved Permanently`.
- `GET /online/` followed by `HTTP/1.1 200 OK`.
- A request for `/favicon.ico` and an HTTP 200 response.
- No UDP packets using port 1194 were found in this baseline capture.

The capture supports the HTTP-baseline interpretation. A packet capture by itself cannot prove the exact state displayed by the OpenVPN Connect application; retain a screenshot of the disconnected client if the lab instructor requires direct proof that the VPN was disconnected.

### Part B: OpenVPN traffic capture

1. Connect OpenVPN Connect to CloudConnexa.
2. Start a new Wireshark capture on `en0`.
3. Generate network traffic while connected.
4. Stop the capture and use a filter such as:

   ```text
   openvpn || udp.port == 1194
   ```

5. Save it as `Captures/Exp11_VPN_Traffic.pcapng`.

**Observed in the supplied capture:**
- 370 captured packets in total.
- 12 UDP packets use port 1194.
- The accompanying screenshot shows OpenVPN `P_DATA_V2` packets.

**Important interpretation:** The supplied VPN capture also contains readable HTTP traffic to `example.com` on TCP port 80. Therefore, it is accurate to say that OpenVPN tunnel packets were captured, but it would be inaccurate to claim that every packet in this file is encrypted VPN traffic. This may be consistent with split-tunnel routing or other traffic outside the tunnel. Interpret the route of each flow separately.

### Part C: Combined HTTP and OpenVPN capture

1. Keep OpenVPN connected.
2. Start a new capture on `en0`.
3. Visit `http://neverssl.com` and allow the page to load.
4. Stop the capture and apply:

   ```text
   http || openvpn
   ```

5. Save it as `Captures/Exp11_Combined_Traffic.pcapng`.

**Observed in the supplied capture:**
- 532 captured packets in total.
- 34 UDP packets use port 1194.
- HTTP traffic includes:
  - `GET /online HTTP/1.1`;
  - `HTTP/1.1 301 Moved Permanently`;
  - `GET /online/ HTTP/1.1`;
  - `HTTP/1.1 200 OK`;
  - a favicon request and response.
- The capture also contains OpenVPN `P_DATA_V2` packets.

Because the capture was made on the physical interface and the CloudConnexa network was configured with Split Tunnel enabled, the presence of both HTTP and OpenVPN packets is consistent with different flows taking different routes. The capture alone does not establish the routing path for every possible application or destination.

## 5. Useful Wireshark display filters

```text
http
```
Displays packets dissected as HTTP.

```text
openvpn
```
Displays packets dissected as OpenVPN.

```text
udp.port == 1194
```
Displays UDP packets using port 1194.

```text
http || openvpn
```
Displays HTTP and OpenVPN packets together.

## 6. Evidence included

### Screenshots

| File | What it documents |
|---|---|
| `Screenshots/Exp11_Part1_HTTP_Traffic.png` | HTTP request and response details |
| `Screenshots/Exp11_Part2_OpenVPN_Encrypted_Traffic.png` | OpenVPN `P_DATA_V2` packet details |
| `Screenshots/Exp11_Part3_HTTP_vs_OpenVPN.png` | HTTP and OpenVPN packets in the same capture |

### Packet captures

| File | Packet count | Verified observations |
|---|---:|---|
| `Captures/Exp11_HTTP_Baseline.pcapng` | 405 | NeverSSL HTTP request/response; no UDP/1194 packets found |
| `Captures/Exp11_VPN_Traffic.pcapng` | 370 | 12 UDP/1194 packets; also contains clear HTTP to example.com |
| `Captures/Exp11_Combined_Traffic.pcapng` | 532 | 34 UDP/1194 packets and HTTP requests/responses to NeverSSL |

The three PCAPNG files were checked for valid PCAPNG block structure and consistent block lengths. The packet counts above are from the supplied files and may differ in a new capture.

## 7. Observations

1. The HTTP baseline capture contains readable HTTP request lines, headers, and server responses.
2. The VPN capture contains OpenVPN data-channel traffic identified as `P_DATA_V2` over UDP port 1194.
3. The VPN capture also contains some readable HTTP traffic, so not all traffic in that capture should be described as encrypted.
4. The combined capture contains both HTTP packets and OpenVPN packets.
5. Split tunneling affects which flows use the VPN. Seeing OpenVPN packets does not, by itself, prove that all traffic is routed through the VPN.

## 8. Result

OpenVPN CloudConnexa was configured on macOS, and Wireshark captures were collected for an HTTP baseline, OpenVPN traffic, and combined HTTP/OpenVPN traffic. The supplied captures are structurally valid and contain the protocol evidence described above.

## 9. Conclusion

The experiment demonstrated how Wireshark can be used to inspect ordinary HTTP traffic and identify OpenVPN data-channel packets. HTTP request and response headers were readable in the baseline capture, while OpenVPN traffic was identified by its protocol dissection and UDP port. The combined capture showed both protocol types on the physical network interface. The observations also demonstrate why split-tunnel configuration and per-flow routing must be considered before concluding that all network traffic is protected by a VPN.

## 10. Security note

Packet captures can contain IP addresses, hostnames, URLs, and other network metadata. Review them before sharing. Never include VPN profiles, private keys, passwords, or authentication tokens in the submission.
