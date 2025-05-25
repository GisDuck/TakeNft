document.addEventListener("DOMContentLoaded", () => {
      const image = document.getElementById("nft-image"),
      title = document.getElementById("nft-title"),
      collectionName = document.getElementById("collection-name"),
      seeMoreBtn = document.getElementById("btn-see-more"),
      tokenId = document.getElementById("token-id"),
      created = document.getElementById("created"),
      collectionLink = document.getElementById("collection-link"),
      description = document.getElementById("description"),
      holder = document.getElementById("holder"),
      holderAvatar = document.getElementById("holder-avatar")
  

    fetch("/data/nft_info.json")
      .then(res => res.json())
      .then(data => {
        image.src = data.imageUrl;
        image.alt = data.title;
        title.textContent = data.title;
        collectionName.textContent = data.collectionName;
        seeMoreBtn.onclick = () => {
          window.open(data.collectionUrl, "_blank");
        };
        tokenId.textContent = data.tokenId;
        created.textContent = data.createdAt;
        collectionLink.href = data.collectionUrl;
        collectionLink.textContent = data.collectionShort;
        description.textContent = data.description;
        holder.textContent = data.holder;
        holderAvatar.src = data.holderAvatar;
      })
      .catch(err => {
        console.error("Ошибка загрузки данных NFT:", err);
        title.textContent = "Ошибка загрузки данных";
      });
  });
  