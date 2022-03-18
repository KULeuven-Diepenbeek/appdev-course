document.addEventListener("DOMContentLoaded",function() {

	const defaultLang = "Java"

	function devselectorFor(langs) {
		let devselector = `<div class="devselect-btns">`
		devselector += langs.map((datalang, i) => {
			let lang = datalang
			if(lang === "kt") lang = "Kotlin"
			if(lang === "java") lang = "Java"
			if(!lang) lang = defaultLang
			const active = i == 0 ? "active" : ""

			return `
				<span class="btn ${active}" data-lang="${datalang}">${lang}</span>
			`
		}).join('')
				
		devselector += `</div>`
		return devselector
	}

	function switchToLanguage(e) {
		const btn = e.target
		const langToActivate = btn.getAttribute("data-lang");

		[...document.querySelectorAll(".devselect .btn")].forEach(btn => {
			if(btn.getAttribute("data-lang") === langToActivate)
				btn.className = "btn active"
			else
				btn.className = "btn"
		});

		[...document.querySelectorAll(".devselect .highlight")].forEach(highlight => {
			if([...highlight.querySelectorAll("pre code")].some(block => block.getAttribute("data-lang") === langToActivate))
				highlight.style.display = "block"
			else
				highlight.style.display = "none"
		})
	}

	// add button bars to each devselect block
	[...document.querySelectorAll(".devselect")].forEach(e => {
		const langs = [...e.querySelectorAll("pre code")].map(block => {
			return block.getAttribute("data-lang")
		})

		e.innerHTML = devselectorFor(langs) + e.innerHTML
	});

	// add click events to each lang select button
	[...document.querySelectorAll(".devselect-btns .btn")].forEach(btn => {
		btn.addEventListener("click", switchToLanguage)
	})

})