# Pipelined Architectures for Latency Aware Caching With Delayed Hits
##### Simulator in caffeine library

[Links to libery caffeine](https://github.com/ben-manes/caffeine/tree/master)


## Abstract 
Modern computer systems use caching to speed up access to data and make better use of resources. These systems, especially in storage and cloud environments, are diverse, with different types of storage, locations, and caches, leading to inconsistent access times. Applications running on these systems also have varying access patterns, like how often they access data or if they do so in bursts. To handle this complexity, cache systems use complex algorithms that aim to balance conflicting priorities. However, these algorithms are hard to expand, debug, and integrate into software.

Our research introduces a flexible architecture that represents complex algorithms as a series of simple policies. This architecture automatically adjusts the different stages of the policy pipeline to maximize the system's performance for the current workload. This means we can add new strategies while preserving the old ones and only use them when they provide value. We've also developed a new cache algorithm called Burst Cache, which can identify and keep items that come in bursts for a long time, even if they aren't recent or frequent. This is particularly useful for handling brief bursts of data that arrive faster than the cache can process them, leading to delays and increased request times. Integrating this strategy into a cache system without harming performance for non-bursty data is a challenge. Our proposed algorithm can select the best strategy based on the workload's characteristics.

## Introduction 
Caching is a crucial technique to speed up data retrieval by taking advantage of variations in access times among different types of memory and storage. Caches store a small portion of data in a faster storage medium. For instance, DRAM can act as a cache for SSDs, and SSDs can serve as a cache for HDDs. When you access data stored in a cache, it's called a cache hit, while accessing data not in the cache is a cache miss. Cache hits are generally faster because the data is fetched from a quicker storage medium.

Traditionally, cache algorithms aimed to maximize the hit ratio, which is the percentage of accesses served faster by the cache. This works well because many workloads exhibit patterns like recency (recently accessed items are likely to be accessed again) and frequency (items accessed frequently over time are likely to be accessed again). However, in practice, it's challenging to predict which heuristic will be most beneficial. Therefore, adaptive caches often blend frequency and recency and dynamically allocate resources to each heuristic based on the workload's characteristics.
In situations where access times are not consistent and especially when the times it takes to retrieve data that's not in cache (miss times) vary, it's important to note that maximizing the cache hit ratio doesn't necessarily mean minimizing the overall access time. This is particularly relevant in various real-world applications like search engines, data storage systems, mobile apps, DNS, web access, and more. Non-uniform access times typically occur when a storage system is distributed or employs different storage mediums. Additionally, Non-uniform Memory Access (NUMA) systems can result in different access times to various parts of the DRAM for different CPU cores.

When focusing on variable access times within the context of cost-aware caching, we observe an important distinction. Accessing an item while it's being added to the cache is neither a cache hit (because the item isn't in the cache) nor precisely a cache miss (because it's being fetched to the cache). These situations are known as delayed hits, common in storage systems with bursts of requests, web queries, DNS resolutions, or file retrievals. Unlike classical cache algorithms like LRU, which assume instant item admission, our approach aims to create a software-friendly delayed hit-aware cache using a pipelined adaptive architecture and dynamic sizing based on workload characteristics, allowing the incorporation of multiple policies and algorithms for improved performance.

In the context of cost-aware caching, each item in the cache is associated with a certain access cost. These costs can represent various factors such as variations in access times, monetary expenses, power consumption, and so on. The field of cost-aware caching focuses on developing efficient algorithms tailored to this cost-based model. The problem with ad-hoc caching algorithms is that they become outdated as new heuristics and approaches are introduced, and most caching research does not consider the cost-aware model. Consequently, valuable ideas from one field have limited influence on the development of ideas in other fields.
Our contribution involves enhancing the design of latency-aware caches. We propose an adaptive cache management algorithm that incorporates three distinct factors: Recency, Frequency, and Burstiness, all while considering latency. We introduce Burstiness as a new criterion for caching policies, separate from Recency and Frequency.

Our solution operates as a pipeline of simple cache policies, where the output of one policy feeds into the next. To make it adaptive, we divide the cache into smaller segments and employ a novel ghost entries-based hill climber algorithm to move portions between cache algorithms, adjusting their emphasis on Recency, Frequency, or Burstiness. This adaptability allows us to introduce and fine-tune policies based on workload characteristics.

Through extensive simulations, we demonstrate that our algorithm surpasses various state-of-the-art alternatives, is insensitive to initial conditions, and competes favorably with static configurations, occasionally even outperforming the best static setups. This underscores the effectiveness of our adaptivity technique in identifying and leveraging subtle workload trends. Furthermore, our approach outperforms existing methods, even in scenarios where previous algorithms falter, thanks to our more robust use of ghost entries. While it requires additional metadata, the overhead for a small number of blocks is comparable to other works in the field.

## policies
* LRU (Least Recently Used) is a cache eviction algorithm that removes the least recently accessed item when the cache is full
* LFU (Least Frequently Used) is a cache eviction algorithm that removes the least frequently accessed item when the cache is full.
* First-In-First-Out (FIFO): In FIFO, the first item that was added to the cache is the first one to be removed when the cache reaches its limit. It follows a strict queue-like behavior. 
* Random Replacement: This policy selects a random item from the cache to evict when needed. It's simple but lacks any specific strategy.
* Most Recently Used (MRU): MRU evicts the most recently accessed item when the cache is full. It assumes that the most recently accessed item is the most likely to be accessed again soon.
* Adaptive Replacement Cache (ARC): ARC combines elements of both LRU and LFU to adapt to changing access patterns. It dynamically adjusts its behavior based on recent access history.
* Two-Queue Algorithm (2Q): 2Q uses two separate queues, one for recently accessed items (MRU) and the other for frequently accessed items (LFU). It aims to provide a balance between these two aspects of caching.
* Clock or Second-Chance Algorithm: This policy keeps a circular buffer and marks items as they are accessed. When an item needs to be evicted, it looks for the first unmarked (unused) item in the buffer.
* LIRS (Low Inter-reference Recency Set): LIRS is designed to improve upon LRU by distinguishing between items with high and low inter-reference recency. It aims to provide better cache hit rates.
* GDSF (Greedy-Dual Size Frequency): GDSF maintains two separate queues for items and prioritizes eviction based on both size and access frequency.

## Important 
* DRAM, which stands for Dynamic Random-Access Memory, is a type of volatile computer memory that is commonly used in computers and other electronic devices. It is a form of primary or main memory in a computer system and is responsible for temporarily storing data that     the CPU (Central Processing Unit) actively uses during program execution. Regarding the statement "DRAM can be a cache for SSD," this means that DRAM memory can be used as a cache to enhance the performance of Solid-State Drives (SSDs). SSDs are a type of non-volatile storage that is faster than traditional hard disk drives (HDDs) but still not as fast as DRAM. By using DRAM as a cache for frequently accessed data from the SSD, it helps reduce the latency of data access and improves overall system performance. This caching mechanism allows frequently used data to be quickly retrieved from the high-speed DRAM memory, while less frequently accessed data is stored on the slower but higher-capacity SSD. It's a technique used to bridge the speed gap between DRAM and SSD, optimizing data access in a computer system.
* Ad-hoc algorithms refer to algorithms that are specifically designed or tailored for a particular problem or situation without following a general, standardized approach. These algorithms are created on a case-by-case basis to address a specific, often unique, problem or set of conditions. They are typically not part of a broader, established algorithmic framework and are often improvised or custom-built to solve a particular problem at hand.

## Tools <img align="right" width="15%" src="https://github.com/shahaf2284/Final_Degree_Project/assets/122786017/8c99f679-bde1-41a9-a957-51882d86a4bb" /> 
* Spring Boot - is an open-source Java-based framework used for building production-ready, stand-alone, and web-based applications quickly and with minimal configuration. It is part of the larger Spring ecosystem and is designed to simplify the development of Spring   applications by providing a set of pre-configured templates and conventions. Spring Boot makes it easier to create robust, scalable, and maintainable Java applications.
    * [Guide To Caching in Spring](https://www.baeldung.com/spring-cache-tutorial)
    * [Another explanation](https://docs.spring.io/spring-boot/docs/2.1.6.RELEASE/reference/html/boot-features-caching.html#boot-features-caching-provider-caffeine)
    * [Instructions Spring](https://www.javatpoint.com/spring-boot-caching)
      

## REFERENCES
* [Caching Online Video: Analysis and Proposed Algorithm](https://www.researchgate.net/publication/319117728_Caching_Online_Video_Analysis_and_Proposed_Algorithm)
* [Cost-aware caching: optimizing cache provisioning and object placement in ICN](https://perso.telecom-paristech.fr/drossi/paper/rossi14globecom.pdf)
* [Caching with Delayed Hits](https://dl.acm.org/doi/pdf/10.1145/3387514.3405883)
* [Lower Bounds for Caching with Delayed Hits](https://arxiv.org/pdf/2006.00376.pdf)
* [Hyperbolic Caching: Flexible Caching for Web Applications](https://www.usenix.org/system/files/conference/atc17/atc17-blankstein.pdf)
* [Improving WWW Proxies Performance with Greedy-Dual-Size-Frequency Caching Policy](https://eclass.uoa.gr/modules/document/file.php/D245/2015/HPL-98-69R1_GDS.pdf)
* [Storage-Aware Caching:Revisiting Caching for Heterogeneous Storage Systems](https://research.cs.wisc.edu/wind/Publications/storageAware-fast02.pdf)
#### REFERENCES for the policies
* [Book Data Structures & Algorithms in Java](https://everythingcomputerscience.com/books/schoolboek-data_structures_and_algorithms_in_java.pdf)
* [Book Java Concurrency in Practice](https://leon-wtf.github.io/doc/java-concurrency-in-practice.pdf)
* [High-Performance Computing and Networking](https://link-springer-com.ezproxy.bgu.ac.il/book/10.1007/3-540-48228-8)

## More reference use later mybe 
* https://java-design-patterns.com/patterns/caching/


## Questions we are trying to understand about the library
* if it is possible to obtain information about the latency
   * Caffeine library primarily focuses on caching and doesn't directly provide features for measuring or obtaining information about          latency. It is designed to efficiently manage in-memory caches and provide fast access to cached data.
 
~~ssss~~
